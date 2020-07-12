package com.anikinkirill.foodrecipes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(

    var title: String? = null,
    val publisher: String? = null,
    val ingredients: List<String>? = null,
    val recipe_id: String? = null,
    var image_url: String? = null,
    var social_rank: Float? = null

) : Parcelable