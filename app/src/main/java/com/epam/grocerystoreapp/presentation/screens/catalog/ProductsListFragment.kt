package com.epam.grocerystoreapp.presentation.screens.catalog

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentProductsListBinding
import com.epam.grocerystoreapp.presentation.model.ProductListItemAction
import com.epam.grocerystoreapp.presentation.utils.*
import com.epam.grocerystoreapp.presentation.utils.Const.CATEGORY_PRODUCTS_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list) {

    private lateinit var binding: FragmentProductsListBinding

    private lateinit var adapter: ProductsListAdapter

    private val viewModel: ProductsListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsListBinding.bind(view)
        initToolbar()
        initViews()
        initObservers()
    }

    private fun initObservers() {
        viewModel.presentationType.observe(viewLifecycleOwner) { type ->
            when (type) {
                ContentPresentationType.LIST -> {
                    binding.catalogRecyclerView.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.listLookImageButton.setImageResource(R.drawable.ic_baseline_grid_on_24)
                }
                ContentPresentationType.GRID -> {
                    binding.catalogRecyclerView.layoutManager =
                        GridLayoutManager(requireContext(), SPAN_COUNT)
                    binding.listLookImageButton.setImageResource(R.drawable.ic_baseline_list_24)
                }
            }
            binding.catalogRecyclerView.adapter = adapter
            adapter.setUpPresentationType(type)
        }

        viewModel.filter.observe(viewLifecycleOwner) { filterOptionId ->
            binding.filterTextView.setText(getFilterTitleResId(filterOptionId))
        }

        lifecycleScope.launch {
            viewModel.productsFlow
                .collectLatest(adapter::submitData)
        }
    }

    private fun initViews() {
        binding.title.setText(R.string.catalog_frag_title)

        adapter = ProductsListAdapter(::onProductClicked)

        adapter.addLoadStateListener { state ->
            with(binding) {
                progress.visibility = if (state.refresh == LoadState.Loading) VISIBLE else INVISIBLE
            }
        }

        binding.catalogRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ProductsLoaderStateAdapter(),
            footer = ProductsLoaderStateAdapter()
        )

        binding.listLookImageButton.setOnClickListener {
            viewModel.onPresentationTypeChange()
        }

        binding.filterContainerLayout.setOnClickListener { view ->
            val popup = PopupMenu(requireActivity(), view)
            popup.menuInflater.inflate(R.menu.catalog_products_filter_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->
                viewModel.onSorted(menuItem.itemId)
                true
            }
            popup.setOnDismissListener { }
            popup.show()
        }

        binding.buttonSearch.setOnClickListener {
            viewModel.onSearch(binding.searchEditText.text.toString())
            hideKeyboardFrom(activity?.baseContext, requireActivity().currentFocus)
        }

        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                viewModel.onSearch((v as EditText).text.toString())
                hideKeyboardFrom(activity?.baseContext, requireActivity().currentFocus)
                true
            }
            else
                false
        }

        arguments?.getString(CATEGORY_PRODUCTS_KEY)?.let { categoryName ->
            (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = categoryName
        }
    }

    private fun onProductClicked(productListItemAction: ProductListItemAction) {
        when(productListItemAction.actionType) {
            ProductItemAction.GET_INFO -> findNavController().navigate(
                R.id.action_productsFragment_to_productFragment,
                bundleOf(Const.PRODUCT_ID_KEY to productListItemAction.productId)
            )
            ProductItemAction.CHANGE_FAVORITE -> viewModel.changeFavorite(productListItemAction.productId)
            ProductItemAction.ADD_TO_CART -> viewModel.addToCart(productListItemAction.productId)
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

    companion object {
        private const val SPAN_COUNT = 2
    }

}
