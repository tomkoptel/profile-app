package com.olderwold.profile.domain

internal interface DashboardDataSource {
    suspend fun dashboard(): Dashboard
}
