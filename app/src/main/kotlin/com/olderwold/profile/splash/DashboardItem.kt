package com.olderwold.profile.splash

import com.olderwold.profile.domain.Framework

internal sealed class DashboardItem {
    data class Frameworks(val data: List<FrameworkElement>) : DashboardItem() {
        companion object {
            operator fun invoke(frameworks: List<Framework>): Frameworks {
                val elements = frameworks.map(::FrameworkElement)
                return Frameworks(elements)
            }
        }
    }
}
