package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count")
    @Expose
    val count: Int,

    @SerializedName("recipes")
    @Expose
    val recipes: List<Recipe>
)