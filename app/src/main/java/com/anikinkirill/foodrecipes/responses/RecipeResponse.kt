package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeResponse(

    @SerializedName("recipe")
    @Expose
    private val recipe: Recipe

)