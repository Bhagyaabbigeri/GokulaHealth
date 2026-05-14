package com.bhagyashreereddy.gokulahealth.ui.vaccination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination
import com.bhagyashreereddy.gokulahealth.databinding.ItemVaccinationBinding

class VaccinationAdapter(
    private val onToggleComplete: (Vaccination) -> Unit,
    private val onDelete: (Vaccination) -> Unit
) : ListAdapter<Vaccination, VaccinationAdapter.VaccinationViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Vaccination>() {
            override fun areItemsTheSame(oldItem: Vaccination, newItem: Vaccination) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Vaccination, newItem: Vaccination) =
                oldItem == newItem
        }
    }

    inner class VaccinationViewHolder(private val binding: ItemVaccinationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vaccination: Vaccination) {
            binding.tvVaccineName.text = vaccination.vaccineName
            binding.tvScheduledDate.text = "Due: ${vaccination.scheduledDate}"
            binding.cbCompleted.isChecked = vaccination.isCompleted
            
            binding.cbCompleted.setOnClickListener {
                onToggleComplete(vaccination.copy(isCompleted = binding.cbCompleted.isChecked))
            }
            
            binding.btnDeleteVaccine.setOnClickListener {
                onDelete(vaccination)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccinationViewHolder {
        val binding = ItemVaccinationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VaccinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VaccinationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
