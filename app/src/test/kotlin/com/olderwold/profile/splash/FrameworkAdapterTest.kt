package com.olderwold.profile.splash

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.olderwold.profile.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class FrameworkAdapterTest {
    @Test
    fun `view holder binds name and progress values`() {
        val mockContext = mockk<Context>(relaxed = true)
        val frameworkName = mockk<TextView>(relaxed = true)
        val frameworkYears = mockk<ProgressBar>(relaxed = true)
        val frameworkElement = mockk<FrameworkElement>() {
            every { fullInfo(any()) } returns "Gandalf!"
            every { progressInTheSpanOfYears(any(), any()) } returns 50
        }
        val containerView = mockk<View>() {
            every { context } returns mockContext
            every { findViewById<TextView>(R.id.frameworkName) } returns frameworkName
            every { findViewById<ProgressBar>(R.id.frameworkYears) } returns frameworkYears
        }

        FrameworkAdapter.ViewHolder(containerView).bind(frameworkElement)

        verify { frameworkName.text = "Gandalf!" }
        verify { frameworkYears.progress = 50 }
    }
}
