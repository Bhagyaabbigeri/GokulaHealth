package com.bhagyashreereddy.gokulahealth.ui.cattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bhagyashreereddy.gokulahealth.R
import com.bhagyashreereddy.gokulahealth.databinding.FragmentCattleListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CattleListFragment : Fragment() {
    private var _binding: FragmentCattleListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CattleViewModel by viewModels()
    private lateinit var adapter: CattleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCattleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CattleAdapter(
            onItemClick = { cattle ->
                // Navigate to detail
                val action = CattleListFragmentDirections.actionListToDetail(cattle.id)
                findNavController().navigate(action)
            },
            onDeleteClick = { cattle ->
                viewModel.deleteCattle(cattle)
            }
        )

        binding.rvCattle.layoutManager = LinearLayoutManager(context)
        binding.rvCattle.adapter = adapter

        viewModel.allCattle.observe(viewLifecycleOwner) { cattleList ->
            binding.tvTotalCattleCount.text = cattleList.size.toString()
            if (cattleList.isEmpty()) {
                binding.tvEmptyList.visibility = View.VISIBLE
                binding.rvCattle.visibility = View.GONE
            } else {
                binding.tvEmptyList.visibility = View.GONE
                binding.rvCattle.visibility = View.VISIBLE
                adapter.submitList(cattleList)
            }
        }

        binding.fabAddCattle.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_add)
        }

        // Hidden Admin Panel Trigger (Long press on Dashboard Title)
        binding.tvDashboardTitle.setOnLongClickListener {
            findNavController().navigate(R.id.adminPanelFragment)
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
