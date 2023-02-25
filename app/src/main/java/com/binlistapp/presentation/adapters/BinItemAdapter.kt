package com.binlistapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.binlistapp.data.model.entities.BinItem
import com.example.binlistapp.R


class BinItemAdapter : ListAdapter<BinItem, BinItemViewHolder>(DiffCallback()) {

    private var onItemClickListener: ((BinItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (BinItem) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinItemViewHolder {
        return BinItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BinItemViewHolder, position: Int) {
        getItem(position).let { holder.bind(it, onItemClickListener) }
    }
}

class DiffCallback : DiffUtil.ItemCallback<BinItem>() {

    override fun areItemsTheSame(oldItem: BinItem, newItem: BinItem): Boolean {
        return oldItem.binNumber == newItem.binNumber
    }

    override fun areContentsTheSame(oldItem: BinItem, newItem: BinItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}