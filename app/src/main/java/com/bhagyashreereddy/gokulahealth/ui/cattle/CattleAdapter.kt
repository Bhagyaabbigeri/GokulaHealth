package com.bhagyashreereddy.gokulahealth.ui.cattle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle
import com.bhagyashreereddy.gokulahealth.databinding.ItemCattleBinding

class CattleAdapter(
    private val onItemClick: (Cattle) -> Unit,
    private val onDeleteClick: (Cattle) -> Unit
) : ListAdapter<Cattle, CattleAdapter.CattleViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cattle>() {
            override fun areItemsTheSame(oldItem: Cattle, newItem: Cattle) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cattle, newItem: Cattle) =
                oldItem == newItem
        }
    }

    inner class CattleViewHolder(private val binding: ItemCattleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cattle: Cattle) {
            binding.tvCattleName.text = cattle.name
            binding.tvEarTag.text = "Tag: ${cattle.earTagId}"
            binding.tvBreed.text = cattle.breed

            // Load cow image with Glide
            if (cattle.photoPath != null) {
                Glide.with(binding.root)
                    .load(cattle.photoPath)
                    .circleCrop()
                    .into(binding.imgCattle)
            } else {
                binding.imgCattle.setImageResource(android.R.drawable.ic_menu_gallery)
            }

            binding.root.setOnClickListener { onItemClick(cattle) }
            binding.btnDelete.setOnClickListener { onDeleteClick(cattle) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CattleViewHolder {
        val binding = ItemCattleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CattleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CattleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
