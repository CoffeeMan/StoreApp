package com.epam.grocerystoreapp.presentation.screens.product

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentProductBinding
import com.epam.grocerystoreapp.presentation.utils.Const.PRODUCT_IS_QUANTITY_CHANGED
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.productUnitStandardQuantityToString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_product) {

    private lateinit var binding: FragmentProductBinding

    private val viewModel: ProductViewModel by viewModels()

    private var requestManager: RequestManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)
        requestManager = Glide.with(requireContext())
        initToolbar()
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.fragmentProductAddToCartBtn.setOnClickListener {
            viewModel.onAddToCartClicked()
        }

        binding.fragmentProductPlusBtn.setOnClickListener {
            viewModel.onPlusBtnClicked()
        }

        binding.fragmentProductMinusBtn.setOnClickListener {
            viewModel.onMinusBtnClicked()
        }
    }

    private fun initObservers() {
        viewModel.productUi.observe(viewLifecycleOwner) { productUi ->
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = productUi.name

            binding.fragmentProductName.text = productUi.name

            binding.fragmentProductDescription.text = productUi.description

            binding.fragmentProductPriceWithoutDiscount.text = productUi.priceWithoutDiscount

            binding.fragmentProductPriceWithDiscount.text = productUi.priceWithDiscount

            binding.fragmentProductPriceWithoutDiscount.visibility =
                productUi.priceWithoutDiscountVisibility

            binding.fragmentProductUnit.text = String.format(
                getString(R.string.product_unity_format),
                productUnitStandardQuantityToString(
                    unit = productUi.unit,
                    resources = requireContext().resources
                )
            )

            requestManager
                ?.load(productUi.photoUrl)
                ?.placeholder(R.drawable.no_photo)
                ?.error(R.drawable.no_photo)
                ?.into(binding.fragmentProductImage)
        }

        viewModel.quantityString.observe(viewLifecycleOwner) { quantityString ->
            binding.fragmentProductQuantityTv.text = quantityString
        }

        viewModel.isMinusBtnEnabled.observe(viewLifecycleOwner) { isMinusBtnEnabled ->
            binding.fragmentProductMinusBtn.isEnabled = isMinusBtnEnabled
        }

        viewModel.isQuantityChanged.observe(viewLifecycleOwner) { isQuantityChanged ->
            findNavController().previousBackStackEntry?.savedStateHandle
                ?.set(PRODUCT_IS_QUANTITY_CHANGED, isQuantityChanged)
        }

        viewModel.quantityInCart.observe(viewLifecycleOwner) { quantityInCart ->
            binding.fragmentProductQuantityInCartTv.text = quantityInCart
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
            title = ""
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
