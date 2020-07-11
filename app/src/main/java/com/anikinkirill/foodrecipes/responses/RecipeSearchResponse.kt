package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse {

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("recipes")
    @Expose
    lateinit var recipes: List<Recipe>

}