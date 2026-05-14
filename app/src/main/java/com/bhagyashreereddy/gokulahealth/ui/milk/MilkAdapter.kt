package com.bhagyashreereddy.gokulahealth.ui.milk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry
import com.bhagyashreereddy.gokulahealth.databinding.ItemMilkBinding

class MilkAdapter(
    private val onDeleteClick: (MilkEntry) -> Unit
) : ListAdapter<MilkEntry, MilkAdapter.MilkViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MilkEntry>() {
            override fun areItemsTheSame(oldItem: MilkEntry, newItem: MilkEntry) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MilkEntry, newItem: MilkEntry) =
                oldItem == newItem
        }
    }

    inner class MilkViewHolder(private val binding: ItemMilkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: MilkEntry) {
            binding.tvDate.text = entry.date
            binding.tvYield.text = "M: ${entry.morningYield}L | E: ${entry.eveningYield}L | Total: ${entry.totalYield}L"
            binding.btnDelete.setOnClickListener { onDeleteClick(entry) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilkViewHolder {
        val binding = ItemMilkBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MilkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MilkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
