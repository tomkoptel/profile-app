package com.olderwold.profile.splash

import android.content.res.Resources
import com.olderwold.profile.R
import com.olderwold.profile.domain.Framework
import kotlin.math.roundToInt

data class FrameworkElement(private val framework: Framework) {
    fun fullInfo(resources: Resources): String {
        val yearsSpent = framework.yearsSpent
        val yearsString = if (yearsSpent == 0) {
            ""
        } else {
            " ${resources.getQuantityString(R.plurals.years, yearsSpent, yearsSpent)}"
        }

        val monthsSpent = framework.monthsSpent
        val monthsString = if (monthsSpent == 0) {
            ""
        } else {
            " ${resources.getQuantityString(R.plurals.months, monthsSpent, monthsSpent)}"
        }

        return "${framework.name}$yearsString$monthsString"
    }

    fun progressInTheSpanOfYears(maxValue: Int, yearSpan: Int): Int {
        return (framework.monthsSpent.toDouble() * maxValue / yearsToMonths(yearSpan)).roundToInt()
    }

    private fun yearsToMonths(years: Int) = years * 12
}
