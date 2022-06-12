package com.epam.grocerystoreapp.presentation.screens.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.presentation.utils.HomeItemType
import com.epam.grocerystoreapp.databinding.HomeScreenContentLoyaltyCardItemBinding
import com.epam.grocerystoreapp.databinding.HomeScreenContentPromotionsItemBinding
import com.epam.grocerystoreapp.databinding.HomeScreenContentSomethingElseItemBinding
import com.epam.grocerystoreapp.domain.model.home.*
import com.epam.grocerystoreapp.presentation.utils.Const

class HomeItemsAdapter(private val onItemClicked: (HomeItem) -> (Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _items = mutableListOf<HomeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HomeItemType.PROMOTIONS.ordinal -> return PromotionsItemVieHolder(
                viewBinding = HomeScreenContentPromotionsItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            HomeItemType.LOYALTY_CARD.ordinal -> return LoyaltyCardItemVieHolder(
                viewBinding = HomeScreenContentLoyaltyCardItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            HomeItemType.SOMETHING_ELSE.ordinal -> return SomethingElseItemVieHolder(
                viewBinding = HomeScreenContentSomethingElseItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw Exception("Not valid viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (_items[position]) {
            is HomeItem.PromotionItem -> (holder as PromotionsItemVieHolder).bind(_items[position] as HomeItem.PromotionItem)
            is HomeItem.LoyaltyCardItem -> (holder as LoyaltyCardItemVieHolder).bind(_items[position] as HomeItem.LoyaltyCardItem)
            is HomeItem.NewsItem -> (holder as SomethingElseItemVieHolder).bind(_items[position] as HomeItem.NewsItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (_items[position]) {
            is HomeItem.PromotionItem -> HomeItemType.PROMOTIONS.ordinal
            is HomeItem.LoyaltyCardItem -> HomeItemType.LOYALTY_CARD.ordinal
            is HomeItem.NewsItem -> HomeItemType.SOMETHING_ELSE.ordinal
            else -> -1
        }
    }

    override fun getItemCount() = _items.size

    fun updateItems(items: List<HomeItem>) {
        val diffResult = DiffUtil.calculateDiff(HomeScreenDiffUtilItemCallback(_items, items))
        _items.clear()
        _items.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PromotionsItemVieHolder(private val viewBinding: HomeScreenContentPromotionsItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val requestManager = Glide.with(itemView.context)

        fun bind(promotionItem: HomeItem.PromotionItem) {
            viewBinding.promotionsTextView.text = promotionItem.title
            /** Set images */
            try {
                requestManager
                    .load(promotionItem.imageUrl)
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(viewBinding.promotionsPreviewImageView)
            } catch (e: Exception) {
                Log.e(Const.APP_LOG_TAG, e.message.toString())
                viewBinding.promotionsPreviewImageView.setImageResource(R.drawable.no_photo)
            }
            viewBinding.root.setOnClickListener(null)
            viewBinding.root.setOnClickListener {
                onItemClicked(promotionItem)
            }
        }
    }

    inner class LoyaltyCardItemVieHolder(private val viewBinding: HomeScreenContentLoyaltyCardItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val requestManager = Glide.with(itemView.context)

        fun bind(loyaltyCardItem: HomeItem.LoyaltyCardItem) {
            viewBinding.loyaltyCardTitle.text = loyaltyCardItem.title
            /** Set images */
            try {
                requestManager
                    .load(loyaltyCardItem.imageUrl)
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(viewBinding.loyaltyCardPreviewImageView)
            } catch (e: Exception) {
                Log.e(Const.APP_LOG_TAG, e.message.toString())
                viewBinding.loyaltyCardPreviewImageView.setImageResource(R.drawable.no_photo)
            }
            viewBinding.root.setOnClickListener(null)
            viewBinding.root.setOnClickListener {
                onItemClicked(loyaltyCardItem)
            }
        }
    }

    inner class SomethingElseItemVieHolder(private val viewBinding: HomeScreenContentSomethingElseItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val requestManager = Glide.with(itemView.context)

        fun bind(newsItem: HomeItem.NewsItem) {
            viewBinding.promotionsTextView.text = newsItem.title
            /** Set images */
            try {
                requestManager
                    .load(newsItem.imageUrl)
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(viewBinding.promotionsPreviewImageView)
            } catch (e: Exception) {
                Log.e(Const.APP_LOG_TAG, e.message.toString())
                viewBinding.promotionsPreviewImageView.setImageResource(R.drawable.no_photo)
            }
            viewBinding.root.setOnClickListener(null)
            viewBinding.root.setOnClickListener {
                onItemClicked(newsItem)
            }
        }
    }
}