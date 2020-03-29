package com.olderwold.profile.data

import com.olderwold.profile.domain.Dashboard

internal class DashboardMapper(
    private val frameworkMapper: FrameworkMapper,
    private val profileMapper: ProfileMapper
) {
    fun toDomain(dashboardDTO: DashboardDTO): Dashboard {
        val profile = profileMapper.toDomain(dashboardDTO.profile)
        val frameworkList = dashboardDTO.frameworks?.mapNotNull(frameworkMapper::toDomain).orEmpty()
        return Dashboard(profile = profile, frameworks = frameworkList)
    }
}
