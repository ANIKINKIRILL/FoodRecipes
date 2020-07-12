package com.anikinkirill.foodrecipes.repositories

import androidx.lifecycle.LiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.requests.RecipeApiClient

class RecipeRepository private constructor() {

    private val apiClient = RecipeApiClient.instance

    companion object {
        val instance: RecipeRepository by lazy {
            RecipeRepository()
        }
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return apiClient.getRecipesList()
    }

}