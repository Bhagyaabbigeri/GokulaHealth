package com.bhagyashreereddy.gokulahealth.ui.vaccination

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyashreereddy.gokulahealth.data.db.entity.Vaccination
import com.bhagyashreereddy.gokulahealth.databinding.FragmentVaccinationBinding
import com.bhagyashreereddy.gokulahealth.utils.DateUtils
import java.util.*

import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VaccinationFragment : Fragment() {
    private var _binding: FragmentVaccinationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VaccinationViewModel by viewModels()
    private val args: VaccinationFragmentArgs by navArgs()
    private lateinit var adapter: VaccinationAdapter
    private var selectedDate: String = DateUtils.getTodayDate()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVaccinationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupDatePicker()
        
        observeVaccinations(args.cattleId)

        binding.btnAddVaccination.setOnClickListener {
            saveVaccination(args.cattleId)
        }
    }

    private fun setupRecyclerView() {
        adapter = VaccinationAdapter(
            onToggleComplete = { viewModel.updateVaccination(it) },
            onDelete = { viewModel.deleteVaccination(it) }
        )
        binding.rvVaccinations.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@VaccinationFragment.adapter
        }
    }

    private fun setupDatePicker() {
        binding.btnPickVaccineDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, y, m, d ->
                selectedDate = "%04d-%02d-%02d".format(y, m + 1, d)
                binding.btnPickVaccineDate.text = selectedDate
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun observeVaccinations(cattleId: Int) {
        viewModel.getVaccinations(cattleId).observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun saveVaccination(cattleId: Int) {
        val name = binding.etVaccineName.text?.toString()?.trim() ?: ""
        if (name.isEmpty()) return

        val v = Vaccination(
            cattleId = cattleId,
            vaccineName = name,
            scheduledDate = selectedDate
        )
        viewModel.insertVaccination(v)
        binding.etVaccineName.text?.clear()
        Toast.makeText(context, "Scheduled!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
