package com.epam.grocerystoreapp.presentation.screens.profile.profile_edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentProfileEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileEditFragment : Fragment(R.layout.fragment_profile_edit) {

    private lateinit var binding: FragmentProfileEditBinding

    private val viewModel: ProfileEditViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileEditBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
    }

    private fun initViews() {
        binding.profileEditDateOfBirthEditText.setOnClickListener {
            viewModel.onDateOfBirthClicked()
        }

        binding.profileEditApplyChangesBtn.setOnClickListener {
            viewModel.onApplyChangesClicked(
                name = binding.profileEditNameEditText.text.toString(),
                surname = binding.profileEditSurnameEditText.text.toString(),
                dateOfBirth = binding.profileEditDateOfBirthEditText.text.toString(),
            )
        }

        binding.profileEditCancelChangesBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.profileEditDateOfBirthEditText.setOnClickListener {
            viewModel.onDateOfBirthClicked()
        }
    }

    private fun initObservers() {
        viewModel.userUi.observe(viewLifecycleOwner) { userUi ->
            binding.profileEditSurnameEditText.setText(userUi.surname)
            binding.profileEditNameEditText.setText(userUi.name)
            binding.profileEditDateOfBirthEditText.setText(userUi.dateOfBirth)
        }

        viewModel.popBackStack().observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { progressBarVisibility ->
            binding.progressBar.visibility = progressBarVisibility
        }

        viewModel.dateOfBirth.observe(viewLifecycleOwner) { dateOfBirth ->
            binding.profileEditDateOfBirthEditText.setText(dateOfBirth)
        }

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.onDateOfBirthPicked(year, month + 1, dayOfMonth)
        }

        viewModel.datePickerData.observe(viewLifecycleOwner) { datePickerData ->
            if (datePickerData != null) {
                DatePickerDialog(
                    requireContext(),
                    datePickerListener,
                    datePickerData.year,
                    datePickerData.month,
                    datePickerData.day
                ).show()
            }
        }

        viewModel.toastResId().observe(viewLifecycleOwner) { resId ->
            Toast.makeText(
                requireContext(),
                getString(resId),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.profile_edit_title)
            setDisplayHomeAsUpEnabled(true)
            show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return true
    }

}
