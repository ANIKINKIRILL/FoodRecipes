package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository

class RecipeViewModel : ViewModel() {

    private val repository = RecipeRepository.instance
    var mRecipeId = ""

    fun getRecipeById(recipeId: String) {
        mRecipeId = recipeId
        repository.getRecipeById(recipeId)
    }

    fun getRecipe() : LiveData<Recipe> {
        return repository.getRecipe()
    }

}