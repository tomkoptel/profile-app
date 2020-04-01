package com.olderwold.profile.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.profile.R
import kotlinx.android.extensions.LayoutContainer

internal class ContentAdapter(
    var data: List<Item> = emptyList()
) : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.item_content, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    data class Item(@param:ColorRes val color: Int)

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: Item) {
            val color = ContextCompat.getColor(containerView.context, item.color)
            containerView.setBackgroundColor(color)
        }
    }
}
