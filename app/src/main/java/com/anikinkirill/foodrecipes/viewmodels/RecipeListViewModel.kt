package com.anikinkirill.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel : ViewModel() {

    private val repository = RecipeRepository.instance

    private var isViewingRecipes = false
    private var isPerformingRequest = false

    init {
        isViewingRecipes = false
        isPerformingRequest = false
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return repository.getRecipesList()
    }

    fun searchRecipesApi(query: String, page: Int) {
        isViewingRecipes = true
        isPerformingRequest = true
        repository.searchRecipesApi(query, page)
    }

    fun isViewingRecipes() : Boolean = isViewingRecipes

    fun setIsViewingRecipes(isViewingRecipes: Boolean){
        this.isViewingRecipes = isViewingRecipes
    }

    fun isPerformingRequest() : Boolean = isPerformingRequest

    fun setIsPerformingRequest(isPerformingRequest: Boolean) {
        this.isPerformingRequest = isPerformingRequest
    }

    fun onBackPressed() : Boolean {
        if(isPerformingRequest) {
            repository.cancelRequest()
            isPerformingRequest = false
        }
        if(isViewingRecipes){
            isViewingRecipes = false
            return false
        }
        return true
    }

}