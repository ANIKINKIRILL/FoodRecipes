package com.anikinkirill.foodrecipes.requests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.AppExecutors
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.Constants
import java.util.concurrent.TimeUnit

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

    fun searchRecipesApi() {
        val handler = AppExecutors.instance.getNetworkIO().submit {
            // 1. make a api request
            // 2. recipesList.postValue(apiResult)
        }

        AppExecutors.instance.getNetworkIO().schedule({
            // let the user know its timed out
            handler.cancel(true)
        }, Constants.NETWORK_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)

    }

}