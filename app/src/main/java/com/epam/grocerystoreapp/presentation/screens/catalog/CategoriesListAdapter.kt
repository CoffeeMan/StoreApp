package com.epam.grocerystoreapp.presentation.screens.catalog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.epam.grocerystoreapp.databinding.CatalogCategoryGridItemViewBinding
import com.epam.grocerystoreapp.databinding.CatalogCategoryListItemViewBinding
import com.epam.grocerystoreapp.presentation.utils.ContentPresentationType
import com.epam.grocerystoreapp.presentation.utils.getCategoryIconResId
import com.epam.grocerystoreapp.presentation.utils.getCategoryTitleResId

class CategoriesListAdapter(private val onCategoryClicked: (String) -> (Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val _items = mutableListOf<String>()
    private var _type = ContentPresentationType.LIST

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (_type) {
            ContentPresentationType.GRID -> return CatalogCategoryGridItemViewHolder(
                binding = CatalogCategoryGridItemViewBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            ContentPresentationType.LIST -> return CatalogCategoryListItemViewHolder(
                binding = CatalogCategoryListItemViewBinding.inflate(
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
            is CatalogCategoryGridItemViewHolder -> holder.bind(_items[position])
            is CatalogCategoryListItemViewHolder -> holder.bind(_items[position])
        }
    }

    override fun getItemCount() = _items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(items: List<String>) {
        _items.clear()
        _items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpPresentationType(type: ContentPresentationType) {
        _type = type
        notifyDataSetChanged()
    }

    inner class CatalogCategoryGridItemViewHolder(
        private val binding: CatalogCategoryGridItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.categoryTitle.setText(getCategoryTitleResId(category))
            binding.categoryPreviewImage.setImageResource(getCategoryIconResId(category))
            binding.root.setOnClickListener(null)
            binding.root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }

    inner class CatalogCategoryListItemViewHolder(
        private val binding: CatalogCategoryListItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.categoryTitle.setText(getCategoryTitleResId(category))
            binding.categoryPreviewImage.setImageResource(getCategoryIconResId(category))
            binding.root.setOnClickListener(null)
            binding.root.setOnClickListener {
                onCategoryClicked(category)
            }
        }
    }
}