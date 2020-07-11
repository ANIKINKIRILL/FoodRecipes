package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.models.Recipe

class RecipeListViewModel : ViewModel() {

    private val mutableLiveData = MutableLiveData<List<Recipe>>()

    fun getRecipesList() : LiveData<List<Recipe>> {
        return mutableLiveData
    }

}