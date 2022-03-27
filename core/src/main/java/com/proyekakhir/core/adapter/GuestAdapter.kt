package com.proyekakhir.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.proyekakhir.core.R
import com.proyekakhir.core.data.source.local.UserEntity
import com.proyekakhir.core.databinding.GuestItemBinding

class GuestAdapter : PagingDataAdapter<UserEntity, GuestAdapter.ViewHolder>(COMPARATOR) {


    class ViewHolder(binding: GuestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val bin = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = GuestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.bin) {
            tvName.text = getItem(position)?.fullName
            Glide.with(root.context)
                .load(getItem(position)?.avatar)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(profileImage)

            if (checkPrime(getItem(position)?.id!!) == 2) {
                tvPrime.text = root.context.getString(R.string.prime)
            } else {
                tvPrime.text = root.context.getString(R.string.not_prime)
            }
        }
    }

    private fun checkPrime(num: Int): Int {
        var mark = 0
        for (i in num downTo 1) {
            if (num % i == 0) {
                mark++
            }
        }
        return mark
    }
}