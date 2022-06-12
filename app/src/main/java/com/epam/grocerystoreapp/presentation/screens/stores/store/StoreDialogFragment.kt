package com.epam.grocerystoreapp.presentation.screens.stores.store

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.databinding.DialogFragmentStoreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentStoreBinding
    private val viewModel: StoreViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentStoreBinding.inflate(layoutInflater)
        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
    }

    override fun onStart() {
        super.onStart()
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.closeButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initObservers() {
        viewModel.store.observe(this) { store ->
            binding.storeName.text = store.name
            binding.storeAddress.text = store.address
            binding.storeSchedule.text = store.schedule
        }

        viewModel.progressBarVisibility.observe(this) { progressBarVisibility ->
            binding.progressBar.visibility = progressBarVisibility
        }

        viewModel.toastResId().observe(this) { resId ->
            Toast.makeText(
                requireContext(),
                getString(resId),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}
