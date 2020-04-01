package com.olderwold.profile.details

import android.os.Bundle
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.transitionListener
import androidx.constraintlayout.motion.widget.updateStateForConstraintInSet
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.ORIENTATION_HORIZONTAL
import com.olderwold.profile.R
import kotlinx.android.synthetic.main.fragment_details.view.*


class DetailsFragment : Fragment(R.layout.fragment_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setupHeader(checkNotNull(view.heroCarousel))
        setupContent(checkNotNull(view.pageContent))
        setupMotionLayout(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // simulate async operation that alters view visibility
        view?.postDelayed({
            view?.buttonControl?.visibility = View.VISIBLE
        }, 1000)
    }

    private fun setupHeader(carousel: ViewPager2) {
        val headerAdapter = HeaderAdapter()
        carousel.adapter = headerAdapter
        carousel.orientation = ORIENTATION_HORIZONTAL

        headerAdapter.data = listOf(
            HeaderAdapter.Item(resId = R.drawable.solid_black),
            HeaderAdapter.Item(resId = R.drawable.cat),
            HeaderAdapter.Item(resId = R.drawable.welcome_page_languages),
            HeaderAdapter.Item(resId = R.drawable.welcome_page_visit_history)
        )
        headerAdapter.notifyDataSetChanged()
    }

    private fun setupContent(recyclerView: RecyclerView) {
        val contentAdapter = ContentAdapter()
        recyclerView.adapter = contentAdapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.setHasFixedSize(true)

        contentAdapter.data = listOf(
            ContentAdapter.Item(color = android.R.color.white),
            ContentAdapter.Item(color = R.color.grey_light),
            ContentAdapter.Item(color = R.color.brand1_light),
            ContentAdapter.Item(color = R.color.brand1_dark),
            ContentAdapter.Item(color = R.color.brand2_light),
            ContentAdapter.Item(color = R.color.brand3_light)
        )
        contentAdapter.notifyDataSetChanged()
    }

    private fun MotionLayout.configureWindowInsets() {
        whenApplyWindowInsets { _, insets ->
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

    private fun setupMotionLayout(rootView: View) {
        val motionLayout = checkNotNull(rootView.motionLayout)
        val favoriteButton = checkNotNull(rootView.favorite)
        val toolbar = checkNotNull(rootView.toolbar)
        motionLayout.configureWindowInsets()

        val changeStatusBar = alterStatusBarLightStatus(rootView)
        val changeFavButton = alterFavButtonOutlineProvider(favoriteButton)
        val changeToolbarIcon = alterToolbarIcon(toolbar)
        motionLayout.transitionListener(
            onTransitionChange = { _, _, _, progress ->
                changeStatusBar(progress)
                changeFavButton(progress)
                changeToolbarIcon(progress)
            }
        )

        // We need to ensure that status bar is transparent and icons drawn with black colors
        changeStatusBar(0f)
        changeToolbarIcon(0f)
    }

    private fun alterToolbarIcon(toolbar: Toolbar) : (Float) -> Unit {
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

    private fun alterStatusBarLightStatus(rootView: View): (Float) -> Unit {
        val colorPrimaryRes = rootView.getColorFromAttr(R.attr.colorPrimary)
        val colorPrimary = ContextCompat.getColor(rootView.context, colorPrimaryRes)

        return { progress ->
            val alpha = 255 * progress
            val colorWithAlpha = ColorUtils.setAlphaComponent(colorPrimary, alpha.toInt())
            activity?.window?.statusBarColor = colorWithAlpha

            when {
                progress >= 0.33 -> rootView.setLightStatusBar()
                else -> rootView.clearLightStatusBar()
            }
        }
    }

    private fun alterFavButtonOutlineProvider(favButton: CardView): (Float) -> Unit {
        return { progress ->
            when {
                progress >= 0.33 -> favButton.outlineProvider = null
                else -> favButton.outlineProvider = ViewOutlineProvider.BACKGROUND
            }
        }
    }
}
