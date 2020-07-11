package com.anikinkirill.foodrecipes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(

    val title: String? = null,
    val publisher: String? = null,
    val ingredients: List<String>? = null,
    val recipe_id: String? = null,
    val image_url: String? = null,
    val social_rank: Float? = null

) : Parcelable