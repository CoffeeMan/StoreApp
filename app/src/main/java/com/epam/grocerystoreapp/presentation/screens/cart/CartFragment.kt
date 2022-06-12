package com.epam.grocerystoreapp.presentation.screens.cart

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentCartBinding
import com.epam.grocerystoreapp.presentation.utils.Const.PRODUCT_ID_KEY
import com.epam.grocerystoreapp.presentation.utils.Const.PRODUCT_IS_QUANTITY_CHANGED
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels()

    private val adapter = CartItemAdapter(
        onItemClick = { cartItemUi ->
            findNavController().navigate(
                resId = R.id.action_cartFragment_to_productFragment,
                args = bundleOf(PRODUCT_ID_KEY to cartItemUi.id)
            )
        },
        onDeleteClick = { cartItemUi ->
            viewModel.onDeleteClicked(cartItemUi)
        },
        onPlusClick = { cartItemUi ->
            viewModel.onPlusClicked(cartItemUi)
        },
        onMinusClick = { cartItemUi ->
            viewModel.onMinusClicked(cartItemUi)
        },
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
        viewModel.onViewCreated()
    }

    private fun initViews() {
        binding.fragmentCartRecyclerView.adapter = adapter

        val divider = AppCompatResources.getDrawable(
            requireContext(),
            R.drawable.cart_recycler_divider
        )

        val cartItemDecoration = divider?.let { drawable ->
            CartItemDecoration(
                drawable
            )
        }

        cartItemDecoration?.let { decoration ->
            binding.fragmentCartRecyclerView.addItemDecoration(decoration)
        }
    }

    private fun initObservers() {
        viewModel.cartItemsUi.observe(viewLifecycleOwner) { cartItemsUi ->
            adapter.submitList(cartItemsUi)
        }

        viewModel.cartCost.observe(viewLifecycleOwner) { cartCost ->
            binding.fragmentCartCartCost.text = cartCost
        }

        viewModel.progressBarVisibility.observe(viewLifecycleOwner) { progressVisibility ->
            binding.progressBar.visibility = progressVisibility
        }

        viewModel.toastResId().observe(viewLifecycleOwner) { resId ->
            Toast.makeText(
                requireContext(),
                getString(resId),
                Toast.LENGTH_SHORT
            ).show()
        }

        findNavController().currentBackStackEntry?.savedStateHandle
            ?.getLiveData<Boolean>(PRODUCT_IS_QUANTITY_CHANGED)
            ?.observe(viewLifecycleOwner) { isQuantityChanged ->
                viewModel.onProductFragmentResult(isQuantityChanged)
            }
    }

    private fun initToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.cart_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

    private fun confirmClearCart() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.cart_confirm_clear_title))
            .setPositiveButton(getString(R.string.cart_confirm_clear_positive_btn_text)) { _, _ ->
                viewModel.onClearCartClicked()
            }
            .setNegativeButton(getString(R.string.cart_confirm_clear_negative_btn_text), null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearCart -> {
                confirmClearCart()
            }
        }
        return true
    }

}
