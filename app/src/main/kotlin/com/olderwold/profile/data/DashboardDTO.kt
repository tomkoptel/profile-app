package com.olderwold.profile.data

import kotlinx.serialization.Serializable

@Serializable
internal data class DashboardDTO(
    val profile: ProfileDTO? = null,
    val frameworks: List<FrameworkDTO?>? = null
)

@Serializable
internal data class FrameworkDTO(
    val name: String? = null,
    val startDate: Long? = null,
    val endDate: Long? = null
)

@Serializable
internal data class ProfileDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val avatarUrl: String? = null,
    val profileUrl: String? = null
)
