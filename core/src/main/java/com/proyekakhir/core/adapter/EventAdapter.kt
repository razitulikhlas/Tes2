package com.proyekakhir.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.proyekakhir.core.R
import com.proyekakhir.core.databinding.ListItemBinding
import com.proyekakhir.core.domain.model.Events

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    private var listEvent = ArrayList<Events>()
    private lateinit var eventCallback: EventCallback

    class ViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val bind = binding
    }

    fun setListener(eventCallback: EventCallback){
        this.eventCallback = eventCallback
    }

    fun setData(listEvent: List<Events>) {
        this.listEvent.clear()
        this.listEvent.addAll(listEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = listEvent[position]
        with(holder.bind) {
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

    interface EventCallback {
        fun onClick(event: Events)
    }
}