package com.proyekakhir.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.proyekakhir.core.databinding.ItemLoadStateBinding

class GuestLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<GuestLoadStateAdapter.ViewHolder>(){

    class ViewHolder( binding: ItemLoadStateBinding,
                      retry: () -> Unit):RecyclerView.ViewHolder(binding.root) {
        val bind = binding
        val ret = retry
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            holder.bind.errorMsg.text = loadState.error.localizedMessage
        }
        with(holder.bind){
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState !is LoadState.Loading
            errorMsg.isVisible = loadState !is LoadState.Loading

            retryButton.setOnClickListener {
              holder.ret.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding = ItemLoadStateBinding.bind(view.root)
        return ViewHolder(binding, retry)
    }

}