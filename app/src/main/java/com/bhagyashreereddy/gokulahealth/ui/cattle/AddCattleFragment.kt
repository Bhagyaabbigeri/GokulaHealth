package com.bhagyashreereddy.gokulahealth.ui.cattle

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bhagyashreereddy.gokulahealth.data.db.entity.Cattle
import com.bhagyashreereddy.gokulahealth.data.db.entity.Factory
import com.bhagyashreereddy.gokulahealth.databinding.FragmentAddCattleBinding
import com.bhagyashreereddy.gokulahealth.ui.factory.FactoryViewModel
import com.bhagyashreereddy.gokulahealth.utils.ImageUtils

class AddCattleFragment : Fragment() {

    private var _binding: FragmentAddCattleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CattleViewModel by viewModels()
    private val factoryViewModel: FactoryViewModel by viewModels()
    
    private var savedImagePath: String? = null
    private var selectedFactoryId: Int? = null
    private var factoriesList: List<Factory> = emptyList()

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            binding.imgCattlePhoto.setImageURI(it)
            savedImagePath = ImageUtils.saveImageToInternalStorage(requireContext(), it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddCattleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFactorySpinner()

        binding.btnSelectPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSaveCattle.setOnClickListener {
            saveCattle()
        }
    }

    private fun setupFactorySpinner() {
        factoryViewModel.allFactories.observe(viewLifecycleOwner) { factories ->
            factoriesList = factories
            val names = factories.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, names)
            binding.spinnerFactory.setAdapter(adapter)
        }

        binding.spinnerFactory.setOnItemClickListener { _, _, position, _ ->
            selectedFactoryId = factoriesList[position].id
        }
    }

    private fun saveCattle() {
        val name = binding.etCattleName.text.toString().trim()
        val earTag = binding.etEarTag.text.toString().trim()
        val breed = binding.etBreed.text.toString().trim()
        val dob = binding.etDateOfBirth.text.toString().trim()

        if (name.isEmpty()) {
            binding.etCattleName.error = "Name is required"
            return
        }
        if (earTag.isEmpty()) {
            binding.etEarTag.error = "Ear Tag ID is required"
            return
        }

        val cattle = Cattle(
            factoryId = selectedFactoryId,
            name = name,
            earTagId = earTag,
            breed = breed,
            dateOfBirth = dob,
            photoPath = savedImagePath
        )

        viewModel.insertCattle(cattle)
        Toast.makeText(context, "✅ $name added successfully!", Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
