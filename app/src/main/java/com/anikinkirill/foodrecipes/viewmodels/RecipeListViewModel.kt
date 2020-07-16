package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.ViewState

class RecipeListViewModel : ViewModel() {

    private val mViewState = MutableLiveData<ViewState>()

    init {
        mViewState.value = ViewState.CATEGORIES
    }

    fun viewState() = mViewState

}