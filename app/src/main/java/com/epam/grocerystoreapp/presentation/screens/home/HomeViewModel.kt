package com.epam.grocerystoreapp.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.domain.model.home.HomeItem
import com.epam.grocerystoreapp.domain.model.home.HomeItem.NewsItem
import com.epam.grocerystoreapp.domain.model.home.HomeItem.PromotionItem
import com.epam.grocerystoreapp.domain.use_cases.auth.GetUserUseCase
import com.epam.grocerystoreapp.domain.use_cases.home.GetNewsItemsUseCase
import com.epam.grocerystoreapp.domain.use_cases.home.GetPromoItemsUseCase
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsItemsUseCase: GetNewsItemsUseCase,
    private val getPromoItemsUseCase: GetPromoItemsUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val _promotionsItems = MutableLiveData<List<PromotionItem>>()
    val promotionsItems: LiveData<List<PromotionItem>> = _promotionsItems

    private var _userId = ""

    private val _newsItems = MutableLiveData<List<NewsItem>>()
    val newsItems: LiveData<List<NewsItem>> = _newsItems

    private val clickedPromotionItemSingleEvent = SingleLiveEvent<PromotionItem>()
    fun clickedPromotionItem() = clickedPromotionItemSingleEvent

    private val clickedNewsItemSingleEvent = SingleLiveEvent<NewsItem>()
    fun clickedNewsItem() = clickedNewsItemSingleEvent

    private val clickedLoyaltyCardSingleEvent = SingleLiveEvent<String>()
    fun clickedLoyaltyCard() = clickedLoyaltyCardSingleEvent

    fun onHomeItemClicked(item: HomeItem) {
        when (item) {
            is PromotionItem -> clickedPromotionItemSingleEvent.value = item
            is NewsItem -> clickedNewsItemSingleEvent.value = item
        }
    }

    fun onLoyaltyCardClicked() {
        clickedLoyaltyCardSingleEvent.value = _userId
    }

    init {
        viewModelScope.launch {
            getNewsItems()
            getPromoItems()
            getLoyaltyCard()
        }
    }

    private suspend fun getNewsItems() {
        getNewsItemsUseCase().let { newsItems ->
            _newsItems.value = newsItems
        }
    }

    private suspend fun getPromoItems() {
        getPromoItemsUseCase().let { promotionItems ->
            _promotionsItems.value = promotionItems
        }
    }

    private suspend fun getLoyaltyCard() {
        _userId = getUserUseCase().id
    }

}
