package com.anikinkirill.foodrecipes.requests

import com.anikinkirill.foodrecipes.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    private val retrofit: Retrofit by lazy {
        retrofitBuilder.build()
    }

    val recipeApi: RecipeApi by lazy {
        retrofit.create(RecipeApi::class.java)
    }

}