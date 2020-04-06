package com.olderwold.profile.details

import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.ViewOutlineProvider
import androidx.cardview.widget.CardView
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

        val changeFavButton = alterFavButtonOutlineProvider(checkNotNull(view.favorite))
        makeNavigationBarTransparent(
                motionLayout = checkNotNull(view.motionLayout),
                toolbar = checkNotNull(view.toolbar),
                onMotionLayoutProgressChange = changeFavButton
        )
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

    private fun alterFavButtonOutlineProvider(favButton: CardView): (Float) -> Unit {
        return { progress ->
            when {
                progress >= 0.33 -> favButton.outlineProvider = null
                else -> favButton.outlineProvider = ViewOutlineProvider.BACKGROUND
            }
        }
    }
}
