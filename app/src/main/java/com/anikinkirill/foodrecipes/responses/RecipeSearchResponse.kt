package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count")
    @Expose
    private val count: Int,

    @SerializedName("recipes")
    @Expose
    private val recipes: List<Recipe>
)