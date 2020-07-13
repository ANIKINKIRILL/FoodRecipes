package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository.instance
    var mRecipeId = ""
    private var mDidRetrieveRecipe = false

    init {
        mDidRetrieveRecipe = false
    }

    fun getRecipeById(recipeId: String) {
        mRecipeId = recipeId
        repository.getRecipeById(recipeId)
    }

    fun getRecipe() : LiveData<Recipe> {
        return repository.getRecipe()
    }

    fun getNetworkTimeout() : LiveData<Boolean> = repository.getNetworkTimeout()

    fun setDidRetrieveRecipe(didRetrieveRecipe: Boolean) {
        mDidRetrieveRecipe = didRetrieveRecipe
    }

    fun getDidRetrieveRecipe() = mDidRetrieveRecipe

}