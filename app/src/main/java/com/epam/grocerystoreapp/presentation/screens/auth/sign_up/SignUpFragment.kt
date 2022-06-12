package com.epam.grocerystoreapp.presentation.screens.auth.sign_up

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentSignUpBinding
import com.epam.grocerystoreapp.presentation.utils.Const.AUTH_EMAIL_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
    }

    private fun initViews() {
        binding.signUpDateOfBirthEditText.setOnClickListener {
            viewModel.onDateOfBirthClicked()
        }

        binding.signUpBtn.setOnClickListener {
            viewModel.onSignUpBtnClicked(
                name = binding.signUpNameEditText.text.toString(),
                surname = binding.signUpSurnameEditText.text.toString(),
                dateOfBirth = binding.signUpDateOfBirthEditText.text.toString(),
                email = binding.signUpEmailEditText.text.toString(),
                password = binding.signUpPasswordEditText.text.toString(),
                repeatPassword = binding.signUpRepeatPasswordEditText.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(
                navigationId,
                bundleOf(AUTH_EMAIL_KEY to binding.signUpEmailEditText.text.toString())
            )
        }

        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { progressBarVisibility ->
            binding.progressBar.visibility = progressBarVisibility
        }

        viewModel.dateOfBirth.observe(viewLifecycleOwner) { dateOfBirth ->
            binding.signUpDateOfBirthEditText.setText(dateOfBirth)
        }

        val datePickerListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
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
            title = getString(R.string.auth_sign_up_title)
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
