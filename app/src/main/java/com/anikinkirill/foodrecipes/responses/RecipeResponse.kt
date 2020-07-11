package com.anikinkirill.foodrecipes.responses

import com.anikinkirill.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeResponse(

    @SerializedName("recipe")
    @Expose
    val recipe: Recipe

)