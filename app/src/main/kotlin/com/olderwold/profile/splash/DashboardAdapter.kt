package com.olderwold.profile.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.profile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_frameworks.view.*

internal class DashboardAdapter(
    var data: List<DashboardItem> = emptyList()
) : RecyclerView.Adapter<DashboardAdapter.BaseViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is DashboardItem.Frameworks -> R.layout.item_frameworks
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.item_frameworks -> FrameworksViewHolder(
                rootView,
                viewPool
            )
            else -> throw IllegalArgumentException("Please provide proper mapping for layout $viewType")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class FrameworksViewHolder(
        override val containerView: View,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : BaseViewHolder(containerView) {
        private val frameworkAdapter = FrameworkAdapter()
            .apply {
            setHasStableIds(true)
        }

        init {
            containerView.frameworks?.apply {
                setHasFixedSize(true)
                setRecycledViewPool(viewPool)
                layoutManager = LinearLayoutManager(context)
                adapter = frameworkAdapter
            }
        }

        override fun bind(item: DashboardItem) {
            if (item is DashboardItem.Frameworks) {
                frameworkAdapter.data = item.data
                frameworkAdapter.notifyDataSetChanged()
            }
        }
    }

    abstract class BaseViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        abstract fun bind(item: DashboardItem)
    }
}
