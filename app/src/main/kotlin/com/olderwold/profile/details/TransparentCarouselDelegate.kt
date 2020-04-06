package com.olderwold.profile.details

import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.transitionListener
import androidx.constraintlayout.motion.widget.updateStateForConstraintInSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.olderwold.profile.R

internal fun Fragment.makeNavigationBarTransparent(
    motionLayout: MotionLayout,
    toolbar: Toolbar,
    onMotionLayoutProgressChange: (Float) -> Unit = {}
) {
    val delegate = TransparentCarouselDelegate(motionLayout, toolbar, onMotionLayoutProgressChange)
    viewLifecycleOwner.lifecycle.addObserver(delegate)
}

internal class TransparentCarouselDelegate(
    private val motionLayout: MotionLayout,
    private val toolbar: Toolbar,
    private val onMotionLayoutProgressChange: (Float) -> Unit = {}
) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        configureWindowInsets()

        val changeStatusBar = alterStatusBarLightStatus()
        val changeToolbarIcon = alterToolbarIcon(toolbar)
        motionLayout.transitionListener(
            onTransitionChange = { _, _, _, progress ->
                changeStatusBar(progress)
                changeToolbarIcon(progress)
                onMotionLayoutProgressChange(progress)
            }
        )

        // We need to ensure that status bar is transparent and icons drawn with black colors
        changeStatusBar(0f)
        changeToolbarIcon(0f)
    }

    private fun configureWindowInsets() {
        motionLayout.whenApplyWindowInsets { _, insets ->
            motionLayout.updateStateForConstraintInSet(
                stateId = R.id.start,
                constraintId = R.id.appBar
            ) { appBarConstraint ->
                appBarConstraint.layout.topMargin = insets.systemWindowInsetTop
            }
            motionLayout.updateStateForConstraintInSet(
                stateId = R.id.end,
                constraintId = R.id.appBar
            ) { appBarConstraint ->
                appBarConstraint.layout.topMargin = insets.systemWindowInsetTop
            }
        }
    }

    private fun alterToolbarIcon(toolbar: Toolbar): (Float) -> Unit {
        val lightColor = toolbar.getColorFromAttr(android.R.attr.windowBackground)
        val lightDrawable = toolbar.getTintedDrawableFrom(R.drawable.ic_back, lightColor)

        val darkColor = toolbar.getColorFromAttr(android.R.attr.textColorPrimary)
        val darkDrawable = toolbar.getTintedDrawableFrom(R.drawable.ic_back, darkColor)

        return { progress ->
            if (darkDrawable != null && lightDrawable != null) {
                when {
                    progress >= 0.33 -> toolbar.navigationIcon = darkDrawable
                    else -> toolbar.navigationIcon = lightDrawable
                }
            }
        }
    }

    private fun alterStatusBarLightStatus(): (Float) -> Unit {
        val colorPrimaryRes = motionLayout.getColorFromAttr(R.attr.colorPrimary)
        val colorPrimary = ContextCompat.getColor(motionLayout.context, colorPrimaryRes)
        val window = checkNotNull(motionLayout.activity()?.window) {
            "We should be attached to activity at this stage. Did you forget to inflate layout?"
        }

        return { progress ->
            val alpha = 255 * progress
            val colorWithAlpha = ColorUtils.setAlphaComponent(colorPrimary, alpha.toInt())
            window.statusBarColor = colorWithAlpha

            when {
                progress >= 0.33 -> motionLayout.setLightStatusBar()
                else -> motionLayout.clearLightStatusBar()
            }
        }
    }
}
