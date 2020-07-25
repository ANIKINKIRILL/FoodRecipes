package com.anikinkirill.foodrecipes.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "recipes")
data class Recipe(

    @PrimaryKey
    @NonNull
    val recipe_id: String = "",
    var title: String? = null,
    val publisher: String? = null,
    val ingredients: Array<String>? = null,
    var image_url: String? = null,
    var social_rank: Float? = null,
    var timestamp: Int? = null

) : Parcelable