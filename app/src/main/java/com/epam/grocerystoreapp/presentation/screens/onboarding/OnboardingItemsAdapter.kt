package com.epam.grocerystoreapp.presentation.screens.onboarding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epam.grocerystoreapp.domain.model.onboarding.OnboardingItem
import com.epam.grocerystoreapp.databinding.OnboardingItemLayoutBinding

class OnboardingItemsAdapter :
    RecyclerView.Adapter<OnboardingItemsAdapter.OnboardingItemViewHolder>() {

    private val _items = mutableListOf<OnboardingItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        return OnboardingItemViewHolder(
            viewBinding = OnboardingItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int {
        return _items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<OnboardingItem>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    inner class OnboardingItemViewHolder(private val viewBinding: OnboardingItemLayoutBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(onboardingItem: OnboardingItem) {
            viewBinding.onboardingItemPreview.setImageResource(onboardingItem.imgResId)
            viewBinding.onboardingItemTitleTextView.setText(onboardingItem.titleResId)
            viewBinding.onboardingItemDescriptionTextView.setText(onboardingItem.descriptionResId)
        }
    }
}