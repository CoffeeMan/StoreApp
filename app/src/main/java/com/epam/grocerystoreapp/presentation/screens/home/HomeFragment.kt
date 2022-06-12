package com.epam.grocerystoreapp.presentation.screens.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentHomeBinding
import com.epam.grocerystoreapp.presentation.utils.Const.DESCRIPTION_KEY
import com.epam.grocerystoreapp.presentation.utils.Const.IMAGE_PREVIEW_KEY
import com.epam.grocerystoreapp.presentation.utils.Const.TITLE_KEY
import com.epam.grocerystoreapp.presentation.utils.Const.USER_ID_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var promotionsAdapter: HomeItemsAdapter
    private lateinit var newsAdapter: HomeItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initObservers()
        initViews()
        initToolbar()
        return binding.root
    }

    private fun initObservers() {
        viewModel.promotionsItems.observe(viewLifecycleOwner) { promotionsItems ->
            promotionsItems?.let { promotionsItemsValue ->
                promotionsAdapter.updateItems(promotionsItemsValue)
                binding.promotionsProgress.visibility = View.GONE
            }
        }

        viewModel.newsItems.observe(viewLifecycleOwner) { newsItems ->
            newsItems?.let { newsItemsValue ->
                newsAdapter.updateItems(newsItemsValue)
                binding.newsProgress.visibility = View.GONE
            }
        }

        viewModel.clickedLoyaltyCard().observe(viewLifecycleOwner) { userId ->
            findNavController().navigate(
                R.id.action_homeFragment_to_loyaltyCardItemFragment,
                bundleOf(USER_ID_KEY to userId)
            )
        }

        viewModel.clickedPromotionItem().observe(viewLifecycleOwner) { promotionItem ->
            findNavController().navigate(
                R.id.action_homeFragment_to_promotionItemFragment,
                bundleOf(
                    DESCRIPTION_KEY to promotionItem.description,
                    TITLE_KEY to promotionItem.title,
                    IMAGE_PREVIEW_KEY to promotionItem.imageUrl,
                ),
            )
        }

        viewModel.clickedNewsItem().observe(viewLifecycleOwner) { newsItem ->
            findNavController().navigate(
                R.id.action_homeFragment_to_newsItemFragment,
                bundleOf(
                    DESCRIPTION_KEY to newsItem.description,
                    TITLE_KEY to newsItem.title,
                    IMAGE_PREVIEW_KEY to newsItem.imageUrl,
                ),
            )
        }
    }

    private fun initViews() {
        promotionsAdapter = HomeItemsAdapter(viewModel::onHomeItemClicked)
        newsAdapter = HomeItemsAdapter(viewModel::onHomeItemClicked)
        binding.promotionsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.promotionsRecyclerView.adapter = promotionsAdapter
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsRecyclerView.adapter = newsAdapter
        binding.loyaltyCardImageButton.setOnClickListener {
            viewModel.onLoyaltyCardClicked()
        }
    }

    private fun initToolbar() {
        setHasOptionsMenu(true)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = getString(R.string.home_frag_title)
            setDisplayHomeAsUpEnabled(false)
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.openProfileBtn -> {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
