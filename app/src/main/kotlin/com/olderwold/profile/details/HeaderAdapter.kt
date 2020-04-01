package com.olderwold.profile.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.profile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_preview_image.view.*

internal class HeaderAdapter(
    var data: List<Item> = emptyList()
) : RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.item_preview_image, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    data class Item(@param:DrawableRes val resId: Int)

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Item) {
            containerView.image?.setImageResource(item.resId)
        }
    }
}
