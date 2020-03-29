package com.olderwold.profile.splash

import com.olderwold.profile.domain.Framework
import org.junit.Before
import org.junit.Test
import org.threeten.bp.loadZones
import java.util.*

class FrameworkTest {
    @Before
    fun setUp() {
        loadZones()
    }

    @Test
    fun `compute 10 years spent`() {
        val years10ago = Calendar.getInstance().apply {
            add(Calendar.YEAR, -10)
        }
        val today = Calendar.getInstance().time

        val framework = Framework(name = "Java", startDate = years10ago.time, endDate = today)
        val yearsSpent = framework.yearsSpent

        assert(yearsSpent == 10) { "Expected 10 years, but calculated $yearsSpent" }
    }

    @Test
    fun `compute 5 months spent`() {
        val months5ago = Calendar.getInstance().apply {
            add(Calendar.MONTH, -5)
        }
        val today = Calendar.getInstance().time

        val framework = Framework(name = "Java", startDate = months5ago.time, endDate = today)
        val monthsSpent = framework.monthsSpent

        assert(monthsSpent == 5) { "Expected 5 months, but calculated $monthsSpent" }
    }
}
