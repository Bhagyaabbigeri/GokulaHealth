package com.bhagyashreereddy.gokulahealth.ui.graph

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.bhagyashreereddy.gokulahealth.databinding.FragmentYieldGraphBinding
import com.bhagyashreereddy.gokulahealth.ui.milk.MilkViewModel
import com.bhagyashreereddy.gokulahealth.ui.factory.FactoryViewModel

class YieldGraphFragment : Fragment() {

    private var _binding: FragmentYieldGraphBinding? = null
    private val binding get() = _binding!!
    private val milkViewModel: MilkViewModel by viewModels()
    private val factoryViewModel: FactoryViewModel by viewModels()
    private val args: YieldGraphFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentYieldGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCattleGraph()
        setupFactoryComparison()
    }

    private fun setupCattleGraph() {
        milkViewModel.getMilkEntries(args.cattleId).observe(viewLifecycleOwner) { entries ->
            if (entries.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.lineChart.visibility = View.GONE
                return@observe
            }

            binding.tvNoData.visibility = View.GONE
            binding.lineChart.visibility = View.VISIBLE

            val sortedEntries = entries.sortedBy { it.date }

            val totalYieldEntries = sortedEntries.mapIndexed { index, entry ->
                Entry(index.toFloat(), entry.totalYield)
            }
            val morningEntries = sortedEntries.mapIndexed { index, entry ->
                Entry(index.toFloat(), entry.morningYield)
            }
            val eveningEntries = sortedEntries.mapIndexed { index, entry ->
                Entry(index.toFloat(), entry.eveningYield)
            }

            val totalDataSet = LineDataSet(totalYieldEntries, "Total (L)").apply {
                color = android.graphics.Color.GREEN
                lineWidth = 2.5f
                circleRadius = 4f
                setDrawFilled(true)
                fillAlpha = 30
            }

            val morningDataSet = LineDataSet(morningEntries, "Morning (L)").apply {
                color = android.graphics.Color.YELLOW
                lineWidth = 1.5f
                circleRadius = 3f
            }

            val eveningDataSet = LineDataSet(eveningEntries, "Evening (L)").apply {
                color = android.graphics.Color.BLUE
                lineWidth = 1.5f
                circleRadius = 3f
            }

            val lineData = LineData(totalDataSet, morningDataSet, eveningDataSet)
            val dateLabels = sortedEntries.map { it.date.substring(5) }

            binding.lineChart.apply {
                data = lineData
                description.isEnabled = false
                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(dateLabels)
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    labelRotationAngle = -45f
                }
                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                animateX(1000)
                invalidate()
            }
        }
    }

    private fun setupFactoryComparison() {
        factoryViewModel.factoryComparison.observe(viewLifecycleOwner) { factoryYields ->
            if (factoryYields.isNullOrEmpty()) return@observe

            val barEntries = factoryYields.mapIndexed { index, yield ->
                BarEntry(index.toFloat(), yield.totalYield)
            }

            val dataSet = BarDataSet(barEntries, "Factory Yield (L)").apply {
                colors = listOf(
                    android.graphics.Color.parseColor("#4CAF50"),
                    android.graphics.Color.parseColor("#2196F3"),
                    android.graphics.Color.parseColor("#FF9800")
                )
                valueTextSize = 12f
            }

            val barData = BarData(dataSet)
            val labels = factoryYields.map { it.factoryName }

            binding.barChart.apply {
                data = barData
                description.isEnabled = false
                xAxis.apply {
                    valueFormatter = IndexAxisValueFormatter(labels)
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)
                }
                axisLeft.axisMinimum = 0f
                axisRight.isEnabled = false
                animateY(1000)
                invalidate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
