package com.epam.grocerystoreapp.presentation.screens.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentCatalogBinding
import com.epam.grocerystoreapp.presentation.utils.Const.CATEGORY_PRODUCTS_KEY
import com.epam.grocerystoreapp.presentation.utils.ContentPresentationType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    private val viewModel: CatalogViewModel by viewModels()

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CategoriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        initViews()
        initObservers()
        initToolbar()
        return binding.root
    }

    private fun initObservers(){
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.updateItems(categories)
        }
        viewModel.presentationType.observe(viewLifecycleOwner) { type ->
            when(type) {
                ContentPresentationType.LIST -> {
                    binding.catalogRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.listLookImageButton.setImageResource(R.drawable.ic_baseline_grid_on_24)
                }
                ContentPresentationType.GRID -> {
                    binding.catalogRecyclerView.layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                    binding.listLookImageButton.setImageResource(R.drawable.ic_baseline_list_24)
                }
            }
            binding.catalogRecyclerView.adapter = adapter
            adapter.setUpPresentationType(type)
        }
    }

    private fun onCategoryClicked(category: String) {
        findNavController().navigate(
            R.id.action_catalogFragment_to_productsFragment,
            bundleOf(CATEGORY_PRODUCTS_KEY to category)
        )
    }

    private fun initViews(){
        adapter = CategoriesListAdapter(::onCategoryClicked)
        binding.catalogRecyclerView.adapter = adapter
        binding.listLookImageButton.setOnClickListener {
            viewModel.onPresentationTypeChange()
        }
        binding.title.setText(R.string.catalog_frag_title)
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.catalog_frag_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}