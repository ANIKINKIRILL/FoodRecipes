package com.anikinkirill.foodrecipes.repositories

import android.content.Context
import android.util.Log
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

        private const val TAG = "RecipeRepository"

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
                var recipes = arrayOfNulls<Recipe>(item.recipes.size)
                for(i in item.recipes.indices) {
                    recipes[i] = item.recipes[i]
                }
                for((index, rowId) in recipeDao.insertRecipes(*recipes as Array<out Recipe>).withIndex()) {
                    if(rowId == -1L) {
                        // means that recipe already in the cache
                        // in this case we should not save ingredients or timestamp
                        Log.d(TAG, "saveCallResult: CONFLICT. The recipe already in the cache  ")
                        recipeDao.updateRecipe(
                            recipes[index].recipe_id!!,
                            recipes[index].title!!,
                            recipes[index].publisher!!,
                            recipes[index].image_url!!,
                            recipes[index].social_rank!!
                        )
                    }
                }
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