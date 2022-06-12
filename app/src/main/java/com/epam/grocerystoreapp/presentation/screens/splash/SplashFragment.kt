package com.epam.grocerystoreapp.presentation.screens.splash

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initToolbar()
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(navigationId)
        }
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

}
