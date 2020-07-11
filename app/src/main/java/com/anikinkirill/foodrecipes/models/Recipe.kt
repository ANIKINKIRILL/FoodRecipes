package com.anikinkirill.foodrecipes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(

    private val title: String? = null,
    private val publisher: String? = null,
    private val ingredients: List<String>? = null,
    private val recipe_id: String? = null,
    private val image_url: String? = null,
    private val social_rank: Float? = null

) : Parcelable