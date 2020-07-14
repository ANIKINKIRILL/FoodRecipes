package com.anikinkirill.foodrecipes.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.requests.RecipeApiClient

class RecipeRepository private constructor() {

    private val apiClient = RecipeApiClient.instance
    private var mQuery: String? = null
    private var mPageNumber: Int? = null

    private var isQueryExhausted = MutableLiveData<Boolean>()
    private var mRecipes = MediatorLiveData<List<Recipe>>()

    companion object {
        val instance: RecipeRepository by lazy {
            RecipeRepository()
        }
    }

    init {
        initMediators()
    }

    private fun initMediators() {
        val source = apiClient.getRecipesList()
        mRecipes.addSource(source) {
            if(it != null) {
                mRecipes.value = it
                doneQuery(it)
            }else{
                // search database cache
                doneQuery(null)
            }
        }
    }

    private fun doneQuery(list: List<Recipe>?){
        if(list != null) {
            if (list.size < 30) {
                isQueryExhausted.value = true
            }
        }else{
            isQueryExhausted.value = true
        }
    }

    fun isQueryExhausted() = isQueryExhausted

    fun getRecipesList() : LiveData<List<Recipe>> {
        return mRecipes
    }

    fun searchRecipesApi(query: String, page: Int) {
        var pageNumber = page
        if(pageNumber == 0){
            pageNumber = 1
        }
        mQuery = query
        mPageNumber = pageNumber
        isQueryExhausted.value = false
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

    fun getNetworkTimeout() : LiveData<Boolean> = apiClient.getNetworkTimeout()

}