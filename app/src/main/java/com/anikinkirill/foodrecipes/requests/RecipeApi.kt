package com.anikinkirill.foodrecipes.requests

import androidx.lifecycle.LiveData
import com.anikinkirill.foodrecipes.responses.ApiResponse
import com.anikinkirill.foodrecipes.responses.RecipeResponse
import com.anikinkirill.foodrecipes.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("api/search")
    fun searchRecipe(
        @Query("key") api_key: String,
        @Query("q") query: String,
        @Query("page") page: Int
    ) : LiveData<ApiResponse<RecipeSearchResponse>>

    @GET("api/get")
    fun getRecipeById(
        @Query("key") api_key: String,
        @Query("rId") recipe_id: String
    ) : LiveData<ApiResponse<RecipeResponse>>

}