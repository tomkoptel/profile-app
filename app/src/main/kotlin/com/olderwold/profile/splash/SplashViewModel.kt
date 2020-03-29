package com.olderwold.profile.splash

import androidx.lifecycle.*
import com.olderwold.profile.data.*
import com.olderwold.profile.domain.Profile
import kotlinx.coroutines.launch

internal class SplashViewModel(
    private val dashboardNetworkDataSource: DashboardNetworkDataSource
) : ViewModel() {
    object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
                SplashViewModel(
                    DashboardNetworkDataSource(
                        service = okHttpService(),
                        dashboardMapper = DashboardMapper(FrameworkMapper(), ProfileMapper())
                    )
                ) as T
            } else {
                throw NotImplementedError("Does not support $modelClass")
            }
        }
    }

    private val _state = MutableLiveData<State>().apply {
        value = State.Loading
    }

    val state: LiveData<State> = _state

    fun load() {
        viewModelScope.launch {
            try {
                val result = dashboardNetworkDataSource.dashboard()
                _state.value = State.Success(
                    profile = result.profile,
                    dashboardItems = listOf(DashboardItem.Frameworks(result.frameworks))
                )
            } catch (ex: Exception) {
                _state.value = State.Error(ex)
            }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(
            val profile: Profile,
            val dashboardItems: List<DashboardItem>
        ) : State()
        data class Error(val error: Throwable) : State()
    }
}
