package com.anikinkirill.foodrecipes.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.persistence.RecipeDao
import com.anikinkirill.foodrecipes.persistence.RecipeDatabase
import com.anikinkirill.foodrecipes.requests.ServiceGenerator
import com.anikinkirill.foodrecipes.responses.ApiResponse
import com.anikinkirill.foodrecipes.responses.RecipeSearchResponse
import com.anikinkirill.foodrecipes.util.AppExecutors
import com.anikinkirill.foodrecipes.util.Constants
import com.anikinkirill.foodrecipes.util.NetworkBoundResource
import com.anikinkirill.foodrecipes.util.Resource

class RecipeRepository private constructor(private val context: Context) {

    private val recipeDao: RecipeDao = RecipeDatabase.getDatabase(context).recipeDao()

    companion object {
        var instance: RecipeRepository? = null
        fun getInstance(context: Context) : RecipeRepository {
            if(instance == null) {
                instance = RecipeRepository(context)
            }
            return instance as RecipeRepository
        }
    }

    fun searchRecipesApi(query: String, page: Int) : LiveData<Resource<List<Recipe>>> {
        return object : NetworkBoundResource<List<Recipe>, RecipeSearchResponse>(AppExecutors.instance){
            override fun saveCallResult(item: RecipeSearchResponse) {
                TODO("Not yet implemented")
            }

            override fun shouldFetch(data: List<Recipe>?): Boolean {
                TODO("Not yet implemented")
            }

            override fun loadFromDb(): LiveData<List<Recipe>> {
                return recipeDao.searchRecipes(query, page)
            }

            override fun createCall(): LiveData<ApiResponse<RecipeSearchResponse>> {
                return ServiceGenerator.recipeApi.searchRecipe(Constants.API_KEY, query, page)
            }
        }.asLiveData()
    }

}