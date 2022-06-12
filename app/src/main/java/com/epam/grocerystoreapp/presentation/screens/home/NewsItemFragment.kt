package com.epam.grocerystoreapp.presentation.screens.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.FragmentNewsItemDetailsBinding
import com.epam.grocerystoreapp.presentation.utils.Const

class NewsItemFragment : Fragment(R.layout.fragment_news_item_details) {

    private lateinit var binding: FragmentNewsItemDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsItemDetailsBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        val requestManager = Glide.with(requireActivity())
        arguments?.let { bundle ->
            binding.titleTextView.text = bundle.getString(Const.TITLE_KEY)
            binding.descriptionTextView.text = bundle.getString(Const.DESCRIPTION_KEY)
            /** Set images */
            try {
                requestManager
                    .load(bundle.getString(
                        Const.IMAGE_PREVIEW_KEY
                    ))
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(binding.previewImageView)
            } catch (e: Exception) {
                Log.e(Const.APP_LOG_TAG, e.message.toString())
                binding.previewImageView.setImageResource(R.drawable.no_photo)
            }
        }
        binding.closeImageButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}