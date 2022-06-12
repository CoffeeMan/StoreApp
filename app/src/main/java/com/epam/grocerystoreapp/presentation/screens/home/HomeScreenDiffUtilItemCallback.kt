package com.epam.grocerystoreapp.presentation.screens.home

import androidx.recyclerview.widget.DiffUtil
import com.epam.grocerystoreapp.domain.model.home.HomeItem

class HomeScreenDiffUtilItemCallback(
    private val oldList: List<HomeItem>,
    private val newList: List<HomeItem>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

}