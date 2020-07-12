package com.anikinkirill.foodrecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.models.Recipe

class RecipeRepository private constructor() {

    private var recipesList: MutableLiveData<List<Recipe>> = MutableLiveData()

    companion object {
        val instance: RecipeRepository by lazy {
            RecipeRepository()
        }
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return recipesList
    }

}