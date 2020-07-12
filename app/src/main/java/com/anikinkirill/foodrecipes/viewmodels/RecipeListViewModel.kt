package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {

    private val repository = RecipeRepository.instance

    fun getRecipesList() : LiveData<List<Recipe>> {
        return repository.getRecipesList()
    }

}