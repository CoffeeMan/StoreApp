package com.epam.grocerystoreapp.presentation.screens.onboarding

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private lateinit var binding: FragmentOnboardingBinding

    private val onboardingAdapter = OnboardingItemsAdapter()
    private val viewModel: OnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardingBinding.bind(view)
        initViews()
        initObservers()
        initToolbar()
    }

    private fun initObservers() {
        viewModel.navigationId().observe(viewLifecycleOwner) { navigationId ->
            findNavController().navigate(navigationId)
        }

        viewModel.currentStepId.observe(viewLifecycleOwner) { stepId ->
            setSelectedPageIndicator(stepId)
            if (binding.onboardingContentViewPager.currentItem != stepId)
                binding.onboardingContentViewPager.currentItem = stepId
        }

        viewModel.steps.observe(viewLifecycleOwner) { steps ->
            onboardingAdapter.updateItems(steps)
            setUpOnboardingIndicators(steps.size)
        }

        binding.onboardingContentViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.stepSelected(position)
                }
            }
        )
    }

    private fun initViews() {
        binding.onboardingContentViewPager.adapter = onboardingAdapter
        binding.nextButton.setOnClickListener {
            viewModel.nextStep()
        }
    }

    private fun setUpOnboardingIndicators(counts: Int) {
        for (i in 0 until counts) {
            binding.onboardingIndicatorLayout.addView(
                ImageView(requireActivity()).also { imageView ->
                    imageView.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.onboarding_indicator_inactive_background
                        )
                    )
                },
                i,
                indicatorViewLayoutParams
            )
        }
    }

    private fun setSelectedPageIndicator(selectedPosition: Int) {
        for (i in 0 until binding.onboardingIndicatorLayout.childCount) {
            (binding.onboardingIndicatorLayout.getChildAt(i) as ImageView).setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    if (i == selectedPosition) R.drawable.onbaording_indicator_active_background else R.drawable.onboarding_indicator_inactive_background
                )
            )
        }
    }

    private fun initToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
    }

    companion object {
        private val indicatorViewLayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also { params ->
                params.setMargins(defaultIndicatorViewMargin, 0, defaultIndicatorViewMargin, 0)
            }
        private const val defaultIndicatorViewMargin = 8
    }
}