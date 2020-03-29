package com.olderwold.profile.domain

data class Dashboard(
    val profile: Profile,
    val frameworks: List<Framework> = emptyList()
)
