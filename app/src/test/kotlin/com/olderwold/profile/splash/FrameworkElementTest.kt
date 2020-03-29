package com.olderwold.profile.splash

import android.content.res.Resources
import com.olderwold.profile.R
import com.olderwold.profile.domain.Framework
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class FrameworkElementTest {
    private val resources = mockk<Resources>()
    private val fireMagic = mockk<Framework>(relaxed = true, name = "Mock Framework") {
        every { name } returns "Fire Magic"
    }

    @Test
    fun `test fullInfo, if 0 years 0 months spent`() {
        yearPluralsReturns("")
        monthsPluralsReturns("")
        every { fireMagic.yearsSpent } returns 0
        every { fireMagic.monthsSpent } returns 0

        val info = FrameworkElement(fireMagic).fullInfo(resources)

        assert(info == "Fire Magic") { "Info was $info" }
    }

    @Test
    fun `test fullInfo, if 1 year 0 months spent`() {
        yearPluralsReturns("1 year")
        monthsPluralsReturns("")
        every { fireMagic.yearsSpent } returns 1
        every { fireMagic.monthsSpent } returns 0


        val info = FrameworkElement(fireMagic).fullInfo(resources)

        assert(info == "Fire Magic 1 year") { "Info was $info" }
    }

    @Test
    fun `test fullInfo, if 1 year 1 month spent`() {
        yearPluralsReturns("1 year")
        monthsPluralsReturns("1 month")
        every { fireMagic.yearsSpent } returns 1
        every { fireMagic.monthsSpent } returns 1

        val info = FrameworkElement(fireMagic).fullInfo(resources)

        assert(info == "Fire Magic 1 year 1 month") { "Info was $info" }
    }

    @Test
    fun `test progress ratio, for span of 10 years`() {
        every { fireMagic.monthsSpent } returns 120

        val progressInSpan = progressInTheSpanOf10Years()

        assert(progressInSpan == 100) { "Received progressInSpan=$progressInSpan" }
    }

    @Test
    fun `test progress ratio, for span of 5 years`() {
        every { fireMagic.monthsSpent } returns 60

        val progressInSpan = progressInTheSpanOf10Years()

        assert(progressInSpan == 50) { "Received progressInSpan=$progressInSpan" }
    }

    @Test
    fun `test progress ratio, for span of 0 years`() {
        every { fireMagic.yearsSpent } returns 0

        val progressInSpan = progressInTheSpanOf10Years()

        assert(progressInSpan == 0) { "Received progressInSpan=$progressInSpan" }
    }

    @Test
    fun `test progress ratio, for span of 0 years 6 months`() {
        every { fireMagic.yearsSpent } returns 0
        every { fireMagic.monthsSpent } returns 6

        val progressInSpan = progressInTheSpanOf10Years()

        assert(progressInSpan == 5) { "Received progressInSpan=$progressInSpan" }
    }

    private fun progressInTheSpanOf10Years(): Int {
        val info = FrameworkElement(fireMagic)
        return info.progressInTheSpanOfYears(maxValue = 100, yearSpan = 10)
    }

    private fun monthsPluralsReturns(value: String) {
        every { resources.getQuantityString(R.plurals.months, any(), any()) } returns value
    }

    private fun yearPluralsReturns(value: String) {
        every { resources.getQuantityString(R.plurals.years, any(), any()) } returns value
    }
}
