package com.olderwold.profile.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olderwold.profile.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_framework.view.*

internal class FrameworkAdapter(
    var data: List<FrameworkElement> = emptyList()
) : RecyclerView.Adapter<FrameworkAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val rootView = layoutInflater.inflate(R.layout.item_framework, parent, false)
        return ViewHolder(rootView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        companion object {
            private const val TEN_YEARS_SPAN = 10
        }

        fun bind(framework: FrameworkElement) {
            containerView.frameworkName?.text = framework.fullInfo(containerView.context.resources)
            containerView.frameworkYears?.let { progressBar ->
                progressBar.progress = framework.progressInTheSpanOfYears(
                    maxValue = progressBar.max,
                    yearSpan = TEN_YEARS_SPAN
                )
            }
        }
    }
}
