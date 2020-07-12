package com.anikinkirill.foodrecipes.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.models.Recipe

class RecipeApiClient private constructor() {

    private val recipesList = MutableLiveData<List<Recipe>>()

    companion object {
        val instance: RecipeApiClient by lazy {
            RecipeApiClient()
        }
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return recipesList
    }

}