package com.olderwold.profile.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.olderwold.profile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_page_content.view.*
import kotlinx.android.synthetic.main.item_page_header.view.*

internal class PageAdapter(
    private val recycledViewPool: RecyclerView.RecycledViewPool,
    var data: List<Item> = emptyList()
) : RecyclerView.Adapter<PageAdapter.BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (val pageItem = data[position]) {
            is Item.Header -> R.layout.item_page_header
            is Item.Content -> R.layout.item_page_content
            else -> throw IllegalStateException("Not supported type $pageItem")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(viewType, parent, false)
        return when {
            viewType == R.layout.item_page_header -> HeaderViewHolder(rootView)
            viewType == R.layout.item_page_content -> ContentViewHolder(recycledViewPool, rootView)
            else -> throw IllegalStateException("Not supported type $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    class HeaderViewHolder(
        override val containerView: View
    ) : BaseViewHolder(containerView) {
        private val carouselAdapter = HeaderAdapter()

        init {
            containerView.heroCarousel?.let { carousel ->
                carousel.adapter = carouselAdapter
                carousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
        }

        override fun bind(item: Item) {
            if (item is Item.Header) {
                carouselAdapter.data = item.data
                carouselAdapter.notifyDataSetChanged()
            }
        }
    }

    class ContentViewHolder(
        private val recycledViewPool: RecyclerView.RecycledViewPool,
        override val containerView: View
    ) : BaseViewHolder(containerView) {
        private val contentAdapter = ContentAdapter()

        init {
            containerView.recyclerView?.let { recyclerView ->
                recyclerView.adapter = contentAdapter
                recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
                recyclerView.setHasFixedSize(true)
                recyclerView.setRecycledViewPool(recycledViewPool)
            }
        }

        override fun bind(item: Item) {
            if (item is Item.Content) {
                contentAdapter.data = item.data
                contentAdapter.notifyDataSetChanged()
            }
        }
    }

    abstract class BaseViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun bind(item: Item)
    }

    sealed class Item {
        data class Header(val data: List<HeaderAdapter.Item>) : Item()
        data class Content(val data: List<ContentAdapter.Item>) : Item()
    }
}
