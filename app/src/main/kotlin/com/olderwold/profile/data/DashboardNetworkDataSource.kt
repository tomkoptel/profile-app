package com.olderwold.profile.data

import com.olderwold.profile.domain.DashboardDataSource

internal class DashboardNetworkDataSource(
    private val service: Service,
    private val dashboardMapper: DashboardMapper
) : DashboardDataSource {
    override suspend fun dashboard() = dashboardMapper.toDomain(service.dashboard())
}
