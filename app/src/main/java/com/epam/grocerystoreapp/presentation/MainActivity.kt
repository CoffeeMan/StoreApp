package com.epam.grocerystoreapp.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GroceryStoreApp)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.navHostFragment)
        binding.bottomNavView.setupWithNavController(navController)
        navController.graph.setStartDestination(R.id.homeFragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavView.visibility = when (destination.id) {
                R.id.homeFragment -> View.VISIBLE
                R.id.catalogFragment -> View.VISIBLE
                R.id.productsFragment -> View.VISIBLE
                R.id.scanFragment -> View.VISIBLE
                R.id.cartFragment -> View.VISIBLE
                R.id.storesFragment -> View.VISIBLE
                R.id.storeDialogFragment -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

}
