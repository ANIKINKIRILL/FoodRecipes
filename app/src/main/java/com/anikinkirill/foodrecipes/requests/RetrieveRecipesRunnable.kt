package com.anikinkirill.foodrecipes.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.responses.RecipeSearchResponse
import com.anikinkirill.foodrecipes.util.Constants
import retrofit2.Call

class RetrieveRecipesRunnable(
        private val recipesListLiveData: MutableLiveData<List<Recipe>>,
        private val query: String,
        private val page: Int
) : Runnable {

    companion object {
        private const val TAG = "RetrieveRecipesRunnable"
    }
    
    var cancelRequest = false

    override fun run() {
        val response = getRecipesList(query, page).execute()
        if(cancelRequest){
            return
        }
        if(response.code() == 200){
            val recipesList = response.body()?.recipes
            if(page == 1){
                recipesListLiveData.postValue(recipesList)
            }else{
                val currentRecipesList = recipesListLiveData.value as ArrayList
                currentRecipesList.addAll(recipesList as ArrayList)
                recipesListLiveData.postValue(currentRecipesList)
            }
        }else{
            val error = response.errorBody().toString()
            Log.d(TAG, "run: $error")
            recipesListLiveData.postValue(null)
        }
    }

    private fun getRecipesList(query: String, page: Int) : Call<RecipeSearchResponse> {
        return ServiceGenerator.recipeApi.searchRecipe(Constants.API_KEY, query, page)
    }

    private fun cancelRequest() {
        Log.d(TAG, "cancelRequest: canceling the search request")
        cancelRequest = true
    }

}