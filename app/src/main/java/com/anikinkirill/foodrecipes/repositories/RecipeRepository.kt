package com.anikinkirill.foodrecipes.repositories

import androidx.lifecycle.LiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.requests.RecipeApiClient

class RecipeRepository private constructor() {

    private val apiClient = RecipeApiClient.instance
    private var mQuery: String? = null
    private var mPageNumber: Int? = null

    companion object {
        val instance: RecipeRepository by lazy {
            RecipeRepository()
        }
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return apiClient.getRecipesList()
    }

    fun searchRecipesApi(query: String, page: Int) {
        var pageNumber = page
        if(pageNumber == 0){
            pageNumber = 1
        }
        mQuery = query
        mPageNumber = pageNumber
        apiClient.searchRecipesApi(query, pageNumber)
    }

    fun searchNextPage() {
        searchRecipesApi(mQuery!!, mPageNumber!! + 1)
    }

    fun cancelRequest() {
        apiClient.cancelRequest()
    }

    fun getRecipeById(recipeId: String) {
        apiClient.getRecipeById(recipeId)
    }

    fun getRecipe() : LiveData<Recipe> {
        return apiClient.getRecipe()
    }

}