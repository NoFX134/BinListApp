package com.binlistapp.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.binlistapp.data.model.entities.BinItem
import com.example.binlistapp.databinding.HistoryItemBinding

class BinItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: HistoryItemBinding = HistoryItemBinding.bind(itemView)

    fun bind(currency: BinItem, onItemClickListener: ((BinItem) -> Unit)?) {
        setViews(currency)
        setListeners(currency, onItemClickListener)

    }

    private fun setListeners(
        binItem: BinItem,
        onItemClickListener: ((BinItem) -> Unit)?
    ) {
        itemView.setOnClickListener {
            onItemClickListener?.let {
                it(binItem)
            }
        }
    }

    private fun setViews(binItem: BinItem,)  {
        binding.binHistoryItem.text = binItem.binNumber
    }
}