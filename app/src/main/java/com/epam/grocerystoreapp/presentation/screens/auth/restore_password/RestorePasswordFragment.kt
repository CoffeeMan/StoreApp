package com.epam.grocerystoreapp.presentation.screens.auth.restore_password

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
import com.epam.grocerystoreapp.databinding.FragmentRestorePasswordBinding
import com.epam.grocerystoreapp.presentation.utils.Const
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestorePasswordFragment : Fragment(R.layout.fragment_restore_password) {

    private lateinit var binding: FragmentRestorePasswordBinding

    private val viewModel: RestorePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRestorePasswordBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
    }

    private fun initViews() {
        arguments?.getString(Const.AUTH_EMAIL_KEY)?.let { email ->
            binding.restorePasswordEmailEditText.setText(email)
        }

        binding.restorePasswordBtn.setOnClickListener {
            viewModel.onRestorePasswordClicked(
                binding.restorePasswordEmailEditText.text.toString()
            )
        }
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(
                navigationId,
                bundleOf(Const.AUTH_EMAIL_KEY to binding.restorePasswordEmailEditText.text.toString())
            )
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
            title = getString(R.string.auth_restore_password_title)
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
