package com.anikinkirill.foodrecipes.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.util.AppExecutors
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.Constants
import java.util.concurrent.TimeUnit

class RecipeApiClient private constructor() {

    private val recipesList = MutableLiveData<List<Recipe>>()

    private var retrieveRecipesRunnable: RetrieveRecipesRunnable? = null

    companion object {
        val instance: RecipeApiClient by lazy {
            RecipeApiClient()
        }
    }

    fun getRecipesList() : LiveData<List<Recipe>> {
        return recipesList
    }

    fun searchRecipesApi(query: String, page: Int) {
        if(retrieveRecipesRunnable != null){
            retrieveRecipesRunnable = null
        }
        retrieveRecipesRunnable = RetrieveRecipesRunnable(recipesList, query, page)
        val handler = AppExecutors.instance.getNetworkIO().submit(retrieveRecipesRunnable!!)

        AppExecutors.instance.getNetworkIO().schedule({
            // let the user know its timed out
            handler.cancel(true)
        }, Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

    }

}