package com.bhagyashreereddy.gokulahealth.ui.milk

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyashreereddy.gokulahealth.data.db.entity.MilkEntry
import com.bhagyashreereddy.gokulahealth.databinding.FragmentMilkDiaryBinding
import com.bhagyashreereddy.gokulahealth.utils.DateUtils
import java.util.*

class MilkDiaryFragment : Fragment() {

    private var _binding: FragmentMilkDiaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MilkViewModel by viewModels()
    private val args: MilkDiaryFragmentArgs by navArgs()
    private lateinit var milkAdapter: MilkAdapter
    private var selectedDate: String = DateUtils.getTodayDate()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMilkDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupDatePicker()
        observeMilkEntries()
        updateMonthlyAverage()

        binding.btnAddMilkEntry.setOnClickListener {
            saveMilkEntry()
        }
    }

    private fun setupRecyclerView() {
        milkAdapter = MilkAdapter { entry ->
            viewModel.deleteMilkEntry(entry)
        }
        binding.rvMilkEntries.apply {
            adapter = milkAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupDatePicker() {
        binding.tvSelectedDate.text = selectedDate
        binding.btnPickDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    selectedDate = "%04d-%02d-%02d".format(year, month + 1, day)
                    binding.tvSelectedDate.text = selectedDate
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun observeMilkEntries() {
        viewModel.getMilkEntries(args.cattleId).observe(viewLifecycleOwner) { entries ->
            milkAdapter.submitList(entries)
        }
    }

    private fun saveMilkEntry() {
        val morning = binding.etMorningYield.text.toString().toFloatOrNull()
        val evening = binding.etEveningYield.text.toString().toFloatOrNull()

        if (morning == null || evening == null) {
            Toast.makeText(context, "Please enter valid yield values", Toast.LENGTH_SHORT).show()
            return
        }

        val entry = MilkEntry(
            cattleId = args.cattleId,
            date = selectedDate,
            morningYield = morning,
            eveningYield = evening,
            totalYield = morning + evening
        )

        viewModel.insertMilkEntry(entry)
        Toast.makeText(context, "✅ Milk entry saved!", Toast.LENGTH_SHORT).show()
        clearInputs()
    }

    private fun updateMonthlyAverage() {
        val currentMonth = DateUtils.getCurrentMonth()
        viewModel.fetchMonthlyAverage(args.cattleId, currentMonth)
        viewModel.monthlyAverage.observe(viewLifecycleOwner) { avg ->
            binding.tvMonthlyAverage.text = "📊 THIS MONTH'S AVG: %.1f L/DAY".format(avg)
        }
    }

    private fun clearInputs() {
        binding.etMorningYield.text?.clear()
        binding.etEveningYield.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
