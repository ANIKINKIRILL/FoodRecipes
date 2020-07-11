package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecipeResponse {

    @SerializedName("recipe")
    @Expose
    lateinit var recipe: Recipe

}