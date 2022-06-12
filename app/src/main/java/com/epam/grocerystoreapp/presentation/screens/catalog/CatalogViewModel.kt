package com.epam.grocerystoreapp.presentation.screens.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epam.grocerystoreapp.domain.use_cases.catalog.*
import com.epam.grocerystoreapp.presentation.utils.ContentPresentationType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    getAllCategoriesUseCase: GetAllCategoriesUseCase,
) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> = _categories

    private val _presentationType = MutableLiveData(ContentPresentationType.GRID)
    val presentationType: LiveData<ContentPresentationType> = _presentationType

    init {
        getAllCategoriesUseCase().let { categories ->
            _categories.value = categories
        }
    }

    fun onPresentationTypeChange() {
        _presentationType.value =
            if (_presentationType.value == ContentPresentationType.GRID)
                ContentPresentationType.LIST
            else ContentPresentationType.GRID
    }
}
