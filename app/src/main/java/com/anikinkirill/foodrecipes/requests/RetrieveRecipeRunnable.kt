package com.anikinkirill.foodrecipes.requests

import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.responses.RecipeResponse
import com.anikinkirill.foodrecipes.util.Constants
import retrofit2.Call

class RetrieveRecipeRunnable(
    private val recipeId: String,
    private val recipeLiveData: MutableLiveData<Recipe>
) : Runnable {

    private var cancelRequest = false

    init {
        cancelRequest = false
    }

    override fun run() {
        val response = getRecipeById().execute()
        if(cancelRequest){
            return
        }
        if(response.code() == 200) {
            val recipe = response.body()?.recipe
            recipeLiveData.postValue(recipe)
        }else {
            recipeLiveData.postValue(null)
        }
    }

    private fun getRecipeById() : Call<RecipeResponse> {
        return ServiceGenerator.recipeApi.getRecipeById(Constants.API_KEY, recipeId)
    }

    fun cancelRequest() {
        cancelRequest = true
    }

}