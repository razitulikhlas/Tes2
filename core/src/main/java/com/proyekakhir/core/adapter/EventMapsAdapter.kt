package com.proyekakhir.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.proyekakhir.core.R
import com.proyekakhir.core.databinding.ListItemBinding
import com.proyekakhir.core.domain.model.Events


class EventMapsAdapter : RecyclerView.Adapter<EventMapsAdapter.ViewHolder>() {
    private var listEvent = ArrayList<Events>()

    fun setData(listEvent: List<Events>) {
        this.listEvent.clear()
        this.listEvent.addAll(listEvent)
    }
    class ViewHolder(binding: ListItemBinding) :RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]
        with(holder.bind) {
            tvDescriptions.textSize = 12f
            tvDescriptions.text = event.description
            tvDate.text = event.date
            tvEvent.text = event.title

            Glide.with(root.context)
                .load(event.ImageEvent)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(ivEvent)
        }
    }

    override fun getItemCount(): Int = listEvent.size

}