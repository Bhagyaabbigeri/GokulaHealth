package com.bhagyashreereddy.gokulahealth.ui.cattle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bhagyashreereddy.gokulahealth.databinding.FragmentCattleDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CattleDetailFragment : Fragment() {

    private var _binding: FragmentCattleDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CattleDetailFragmentArgs by navArgs()
    private val viewModel: CattleViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCattleDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCattleById(args.cattleId).observe(viewLifecycleOwner) { cattle ->
            cattle?.let {
                binding.tvNameDetail.text = it.name
                binding.tvBreedDetail.text = it.breed
                if (it.photoPath != null) {
                    Glide.with(this).load(it.photoPath).into(binding.imgCattleDetail)
                }
            }
        }

        binding.btnToMilk.setOnClickListener {
            val action = CattleDetailFragmentDirections.actionDetailToMilk(args.cattleId)
            findNavController().navigate(action)
        }

        binding.btnToGraph.setOnClickListener {
            val action = CattleDetailFragmentDirections.actionDetailToGraph(args.cattleId)
            findNavController().navigate(action)
        }

        binding.btnToVaccine.setOnClickListener {
            val action = CattleDetailFragmentDirections.actionDetailToVaccine(args.cattleId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
