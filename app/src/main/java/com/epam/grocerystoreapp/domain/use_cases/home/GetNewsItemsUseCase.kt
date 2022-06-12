package com.epam.grocerystoreapp.domain.use_cases.home

import com.epam.grocerystoreapp.domain.model.home.HomeItem
import com.epam.grocerystoreapp.domain.repository.HomeRepository
import javax.inject.Inject

class GetNewsItemsUseCase @Inject constructor(
    private val repository: HomeRepository,
) {

    suspend operator fun invoke(): List<HomeItem.NewsItem> {
        return repository.getNewsItems()
    }

}
