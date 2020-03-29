package com.olderwold.profile.data

import com.olderwold.profile.domain.Framework
import java.util.*

internal class FrameworkMapper {
    fun toDomain(frameworkDTO: FrameworkDTO?): Framework? {
        if (frameworkDTO == null) return null

        return Framework(
            name = frameworkDTO.name,
            startDate = frameworkDTO.startDate?.let { Date(it) },
            endDate = frameworkDTO.endDate?.let { Date(it) }
        )
    }
}
