package com.epam.grocerystoreapp.presentation.screens.profile

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
import com.epam.grocerystoreapp.databinding.FragmentProfileBinding
import com.epam.grocerystoreapp.presentation.screens.profile.ProfileDialogFragmentType.*
import com.epam.grocerystoreapp.presentation.utils.Const.PROFILE_DIALOG_FRAGMENT_TYPE_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
        viewModel.onViewCreated()
    }

    private fun initViews() {
        binding.profileEditBtn.setOnClickListener {
            viewModel.onEditProfileClicked()
        }

        binding.profileHelpBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_profileDialogFragment,
                bundleOf(PROFILE_DIALOG_FRAGMENT_TYPE_KEY to HELP),
            )
        }

        binding.profileAboutUsBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_profileDialogFragment,
                bundleOf(PROFILE_DIALOG_FRAGMENT_TYPE_KEY to ABOUT_US),
            )
        }

        binding.profileAboutAppBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_profileFragment_to_profileDialogFragment,
                bundleOf(PROFILE_DIALOG_FRAGMENT_TYPE_KEY to ABOUT_APP),
            )
        }

        binding.profileSignOutBtn.setOnClickListener {
            viewModel.onSignOutClicked()
        }
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(navigationId)
        }

        viewModel.userUi.observe(viewLifecycleOwner) { userUi ->
            binding.profileNameAndSurname.text = "${userUi.name} ${userUi.surname}"
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
            title = getString(R.string.profile_title)
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
