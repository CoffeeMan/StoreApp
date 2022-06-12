package com.epam.grocerystoreapp.presentation.screens.auth.sign_in

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
import com.epam.grocerystoreapp.databinding.FragmentSignInBinding
import com.epam.grocerystoreapp.presentation.utils.Const.AUTH_EMAIL_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding

    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
    }

    private fun initViews() {
        arguments?.getString(AUTH_EMAIL_KEY)?.let { email ->
            binding.signInEmailEditText.setText(email)
        }

        binding.signInForgotPasswordBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_signInFragment_to_restorePasswordFragment,
                bundleOf(AUTH_EMAIL_KEY to binding.signInEmailEditText.text.toString())
            )
        }

        binding.signInBtn.setOnClickListener {
            viewModel.onSignInClicked(
                email = binding.signInEmailEditText.text.toString(),
                password = binding.signInPasswordEditText.text.toString()
            )
        }

        /** For develop */
        binding.signInEmailEditText.setText("max@mail.ru")
        binding.signInPasswordEditText.setText("111111")
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(navigationId)
        }

        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { progressBarVisibility ->
            binding.progressBar.visibility = progressBarVisibility
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
            title = getString(R.string.auth_sign_in_title)
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
