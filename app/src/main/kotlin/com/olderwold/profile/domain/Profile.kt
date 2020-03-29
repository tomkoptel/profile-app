package com.olderwold.profile.domain

data class Profile(
    val firstName: String? = null,
    val lastName: String? = null,
    val profileUrl: String? = null,
    val avatarUrl: String? = null
) {
    fun fullName() = when {
        firstName != null && lastName == null -> firstName
        firstName == null && lastName != null -> lastName
        firstName != null && lastName != null -> "$firstName $lastName"
        else -> null
    }
}
