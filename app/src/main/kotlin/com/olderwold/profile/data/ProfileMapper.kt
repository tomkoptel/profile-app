package com.olderwold.profile.data

import com.olderwold.profile.domain.Profile

internal class ProfileMapper {
    fun toDomain(profileDTO: ProfileDTO?): Profile {
        return if (profileDTO == null) {
            Profile()
        } else {
            Profile(
                firstName = profileDTO.firstName,
                lastName = profileDTO.lastName,
                profileUrl = profileDTO.profileUrl,
                avatarUrl = profileDTO.avatarUrl
            )
        }
    }
}
