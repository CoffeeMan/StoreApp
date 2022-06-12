package com.epam.grocerystoreapp.presentation.screens.auth.start_auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentStartAuthBinding

class StartAuthFragment : Fragment(R.layout.fragment_start_auth) {

    private lateinit var binding: FragmentStartAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStartAuthBinding.bind(view)
        initViews()
        initToolbar()
    }

    private fun initViews() {
        binding.startAuthSignInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startAuthFragment_to_signInFragment)
        }

        binding.startAuthSignUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_startAuthFragment_to_signUpFragment)
        }
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.auth_start_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

}
