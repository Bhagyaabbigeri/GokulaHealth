package com.bhagyashreereddy.gokulahealth.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bhagyashreereddy.gokulahealth.data.db.AppDatabase
import com.bhagyashreereddy.gokulahealth.databinding.FragmentAdminPanelBinding
import com.bhagyashreereddy.gokulahealth.notification.NotificationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminPanelFragment : Fragment() {

    private var _binding: FragmentAdminPanelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAdminPanelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnResetDb.setOnClickListener {
            resetDatabase()
        }

        binding.btnTestNotification.setOnClickListener {
            NotificationHelper.showVaccinationNotification(
                requireContext(),
                "Admin Test",
                "System Test Vaccine",
                "Today",
                999
            )
            Toast.makeText(context, "Test Notification Sent!", Toast.LENGTH_SHORT).show()
        }

        binding.btnAddBulkData.setOnClickListener {
            Toast.makeText(context, "Sample data already handled in AppDatabase!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetDatabase() {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(requireContext())
            db.clearAllTables()
            withContext(Dispatchers.Main) {
                binding.tvDbStatus.text = "DB Status: WIPED"
                Toast.makeText(context, "Database cleared successfully!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
