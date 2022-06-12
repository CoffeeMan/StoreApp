package com.epam.grocerystoreapp.presentation.screens.catalog

import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.CatalogProductGridItemViewBinding
import com.epam.grocerystoreapp.databinding.CatalogProductListItemViewBinding
import com.epam.grocerystoreapp.presentation.model.CatalogProductData
import com.epam.grocerystoreapp.presentation.model.ProductListItemAction
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.ContentPresentationType
import com.epam.grocerystoreapp.presentation.utils.ProductItemAction
import com.epam.grocerystoreapp.presentation.utils.priceDoubleToString

class ProductsListAdapter(private val onProductClicked: (ProductListItemAction) -> (Unit)) :
    PagingDataAdapter<CatalogProductData, RecyclerView.ViewHolder>(ProductsDiffUtilItemCallback) {

    private var _type = ContentPresentationType.LIST

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (_type) {
            ContentPresentationType.GRID -> return CatalogProductGridItemViewHolder(
                binding = CatalogProductGridItemViewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            ContentPresentationType.LIST -> return CatalogProductListItemViewHolder(
                binding = CatalogProductListItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (holder) {
            is CatalogProductGridItemViewHolder -> holder.bind(getItem(position))
            is CatalogProductListItemViewHolder -> holder.bind(getItem(position))
        }
    }

    fun setUpPresentationType(type: ContentPresentationType) {
        _type = type
    }

    inner class CatalogProductGridItemViewHolder(
        private val binding: CatalogProductGridItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val requestManager = Glide.with(itemView.context)
        private var isFavorite = false

        fun bind(product: CatalogProductData?) {
            product?.let {
                binding.productTitle.text = product.catalogProduct.name
                binding.productPrice.text =
                    if (product.hideDiscount) product.catalogProduct.price.priceDoubleToString()
                    else product.catalogProduct.priceWithDiscount.priceDoubleToString()
                binding.productDiscount.text = product.catalogProduct.price.priceDoubleToString()
                binding.discountContentView.visibility = if (product.hideDiscount) GONE else VISIBLE
                isFavorite = product.catalogProduct.isFavorite
                binding.addToFavoriteButton.setImageResource(if(isFavorite) R.drawable.ic_like_enabled else R.drawable.ic_like_disabled)
                binding.root.setOnClickListener(null)
                binding.root.setOnClickListener {
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.GET_INFO))
                }
                binding.addToCartButton.setOnClickListener(null)
                binding.addToCartButton.setOnClickListener {
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.ADD_TO_CART))
                }
                binding.addToFavoriteButton.setOnClickListener(null)
                binding.addToFavoriteButton.setOnClickListener { imageButton ->
                    isFavorite = !isFavorite
                    (imageButton as ImageButton).setImageResource(if(isFavorite) R.drawable.ic_like_enabled else R.drawable.ic_like_disabled)
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.CHANGE_FAVORITE))
                }
                /** Set images */
                try {
                    requestManager
                        .load(product.catalogProduct.photoUrl)
                        .placeholder(R.drawable.no_photo)
                        .error(R.drawable.no_photo)
                        .into(binding.productPreviewImage)
                } catch (e: Exception) {
                    Log.e(Const.APP_LOG_TAG, e.message.toString())
                    binding.productPreviewImage.setImageResource(R.drawable.no_photo)
                }
            }
        }
    }

    inner class CatalogProductListItemViewHolder(
        private val binding: CatalogProductListItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val requestManager = Glide.with(itemView.context)
        private var isFavorite = false

        fun bind(product: CatalogProductData?) {
            product?.let {
                binding.productTitle.text = product.catalogProduct.name
                binding.productPrice.text =
                    if (product.hideDiscount) product.catalogProduct.price.priceDoubleToString()
                    else product.catalogProduct.priceWithDiscount.priceDoubleToString()
                binding.productDiscount.text = product.catalogProduct.price.priceDoubleToString()
                binding.discountContentView.visibility = if (product.hideDiscount) GONE else VISIBLE
                isFavorite = product.catalogProduct.isFavorite
                binding.addToFavoriteButton.setImageResource(if(isFavorite) R.drawable.ic_like_enabled else R.drawable.ic_like_disabled)
                binding.root.setOnClickListener(null)
                binding.root.setOnClickListener {
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.GET_INFO))
                }
                binding.addToCartButton.setOnClickListener(null)
                binding.addToCartButton.setOnClickListener {
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.ADD_TO_CART))
                }
                binding.addToFavoriteButton.setOnClickListener(null)
                binding.addToFavoriteButton.setOnClickListener { imageButton ->
                    isFavorite = !isFavorite
                    (imageButton as ImageButton).setImageResource(if(isFavorite) R.drawable.ic_like_enabled else R.drawable.ic_like_disabled)
                    onProductClicked(ProductListItemAction(product.catalogProduct.id, ProductItemAction.CHANGE_FAVORITE))
                }
                /** Set images */
                try {
                    requestManager
                        .load(product.catalogProduct.photoUrl)
                        .placeholder(R.drawable.no_photo)
                        .error(R.drawable.no_photo)
                        .into(binding.productPreviewImage)
                } catch (e: Exception) {
                    Log.e(Const.APP_LOG_TAG, e.message.toString())
                    binding.productPreviewImage.setImageResource(R.drawable.no_photo)
                }
            }
        }
    }

    private object ProductsDiffUtilItemCallback : DiffUtil.ItemCallback<CatalogProductData>() {
        override fun areItemsTheSame(
            oldItem: CatalogProductData,
            newItem: CatalogProductData
        ): Boolean =
            oldItem.catalogProduct.id == newItem.catalogProduct.id

        override fun areContentsTheSame(
            oldItem: CatalogProductData,
            newItem: CatalogProductData
        ): Boolean =
            oldItem == newItem
    }
}