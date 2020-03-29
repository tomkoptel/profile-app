package com.olderwold.profile.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.setHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.FloatingWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.olderwold.profile.R
import com.olderwold.profile.domain.Profile
import kotlinx.android.synthetic.main.fragment_splash.view.*

internal class SplashFragment : Fragment(R.layout.fragment_splash), FloatingWindow {
    private val viewModel: SplashViewModel by viewModels(factoryProducer = {
        SplashViewModel.Factory
    })
    private val dashboardAdapter = DashboardAdapter().apply {
        setHasStableIds(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.dashboard?.let { recyclerView ->
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = dashboardAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.load()
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is SplashViewModel.State.Loading -> renderLoadingState()
                is SplashViewModel.State.Success -> renderLoadedState(state)
                is SplashViewModel.State.Error -> renderErrorState(state)
            }
        })
    }

    private fun renderLoadedState(state: SplashViewModel.State.Success) {
        view?.progressView?.visibility = View.GONE
        view?.content?.visibility = View.VISIBLE

        dashboardAdapter.data = state.dashboardItems
        dashboardAdapter.notifyDataSetChanged()

        setupProfile(state.profile)
    }

    private fun setupProfile(profile: Profile) {
        view?.avatar?.let { imageView ->
            Glide.with(this)
                .load(profile.avatarUrl)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
        view?.name?.let { textView ->
            profile.fullName()?.let { fullName ->
                textView.setHtml("<u>$fullName</u>")
            }
            textView.setOnClickListener {
                val linkedIn = Uri.parse("https://www.linkedin.com/in/tom-koptel-519b2036/")
                val webProfile = Intent(Intent.ACTION_VIEW, linkedIn)
                startActivity(webProfile)
            }
        }
    }

    private fun renderErrorState(state: SplashViewModel.State.Error) {
        view?.content?.visibility = View.GONE
        Log.e("SplashFragment", "Failed to load data", state.error)
    }

    private fun renderLoadingState() {
        view?.progressView?.visibility = View.VISIBLE
        view?.content?.visibility = View.GONE
    }
}
