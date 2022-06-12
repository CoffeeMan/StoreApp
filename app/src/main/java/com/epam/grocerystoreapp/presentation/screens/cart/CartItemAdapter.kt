package com.epam.grocerystoreapp.presentation.screens.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.databinding.ItemCartBinding
import com.epam.grocerystoreapp.presentation.model.CartItemUi
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.priceDoubleToString
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.productUnitStandardQuantityToString
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.quantityDoubleToString

class CartItemAdapter(
    private val onItemClick: (cartItemUi: CartItemUi) -> Unit,
    private val onDeleteClick: (cartItemUi: CartItemUi) -> Unit,
    private val onPlusClick: (cartItemUi: CartItemUi) -> Unit,
    private val onMinusClick: (cartItemUi: CartItemUi) -> Unit,
) : ListAdapter<CartItemUi, CartItemAdapter.CartItemHolder>(DiffCallback()) {

    inner class CartItemHolder(
        private val binding: ItemCartBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private val requestManager = Glide.with(itemView.context)

        fun bind(cartItemUi: CartItemUi) {
            /** Set text */
            binding.itemCartNameTv.text = cartItemUi.name
            binding.itemCartPriceWithoutDiscountTv.text = priceDoubleToString(cartItemUi.price)
            binding.itemCartPriceWithDiscountTv.text =
                priceDoubleToString(cartItemUi.priceWithDiscount)
            binding.itemCartQuantityTv.text = quantityDoubleToString(
                quantity = cartItemUi.quantity,
                unit = cartItemUi.unit,
                resources = itemView.context.resources
            )

            binding.itemCartUnitTv.text = String.format(
                itemView.context.getString(R.string.product_unity_format),
                productUnitStandardQuantityToString(
                    unit = cartItemUi.unit,
                    resources = itemView.context.resources
                )
            )

            /** Set images */
            try {
                requestManager
                    .load(cartItemUi.photoUrl)
                    .placeholder(R.drawable.no_photo)
                    .error(R.drawable.no_photo)
                    .into(binding.itemCartPhoto)
            } catch (e: Exception) {
                Log.e(Const.APP_LOG_TAG, e.message.toString())
                binding.itemCartPhoto.setImageResource(R.drawable.no_photo)
            }

            when (cartItemUi.quantity) {
                in Double.MIN_VALUE..cartItemUi.unit.discreteValue -> {
                    binding.itemCartMinusBtn.setImageResource(R.drawable.ic_outline_delete_24)
                }
                else -> {
                    binding.itemCartMinusBtn.setImageResource(
                        R.drawable.ic_baseline_remove_circle_outline_24
                    )
                }
            }

            /** Set visibilities */
            binding.itemCartPriceWithoutDiscountTv.visibility =
                cartItemUi.priceWithDiscountVisibility

            binding.itemCartPlusBtn.isEnabled = cartItemUi.variableQuantity
            binding.itemCartMinusBtn.isEnabled = cartItemUi.variableQuantity

            /** Set listeners */
            binding.root.setOnClickListener {
                onItemClick(cartItemUi)
            }
            binding.itemCartDeleteBtn.setOnClickListener {
                onDeleteClick(cartItemUi)
            }
            binding.itemCartPlusBtn.setOnClickListener {
                onPlusClick(cartItemUi)
            }
            binding.itemCartMinusBtn.setOnClickListener {
                onMinusClick(cartItemUi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<CartItemUi>() {
        override fun areItemsTheSame(oldItemUi: CartItemUi, newItemUi: CartItemUi): Boolean {
            return oldItemUi.id == newItemUi.id
        }

        override fun areContentsTheSame(oldItemUi: CartItemUi, newItemUi: CartItemUi): Boolean {
            return oldItemUi == newItemUi
        }
    }

}
