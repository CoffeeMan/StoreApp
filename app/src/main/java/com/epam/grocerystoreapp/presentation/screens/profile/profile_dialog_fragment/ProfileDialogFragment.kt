package com.epam.grocerystoreapp.presentation.screens.profile.profile_dialog_fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.DialogFragmentProfileBinding
import com.epam.grocerystoreapp.presentation.screens.profile.ProfileDialogFragmentType.*
import com.epam.grocerystoreapp.presentation.utils.Const.PROFILE_DIALOG_FRAGMENT_TYPE_KEY

class ProfileDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentProfileBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentProfileBinding.inflate(layoutInflater)
        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        binding.dialogFragmentProfileCloseButton.setOnClickListener {
            findNavController().popBackStack()
        }

        arguments?.get(PROFILE_DIALOG_FRAGMENT_TYPE_KEY)?.let { type ->
            when (type) {
                HELP -> {
                    binding.dialogFragmentProfileTitle.text = getString(R.string.profile_help_title)
                    binding.dialogFragmentProfileDescription.text = getString(R.string.profile_help_description)
                }
                ABOUT_US -> {
                    binding.dialogFragmentProfileTitle.text = getString(R.string.profile_about_us_title)
                    binding.dialogFragmentProfileDescription.text = getString(R.string.profile_about_us_description)
                }
                ABOUT_APP -> {
                    binding.dialogFragmentProfileTitle.text = getString(R.string.profile_about_app_title)
                    binding.dialogFragmentProfileDescription.text = getString(R.string.profile_about_app_description)
                }
            }
        }
    }

}
