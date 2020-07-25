package com.anikinkirill.foodrecipes.adapters

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import com.anikinkirill.foodrecipes.models.Recipe
import com.bumptech.glide.RequestManager
import de.hdodenhof.circleimageview.CircleImageView

class CategoryViewHolder(
    itemView: View,
    private val onRecipeListener: OnRecipeListener,
    private val requestManager: RequestManager
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var categoryImage: CircleImageView = itemView.findViewById(R.id.category_image)
    var categoryTitle: TextView = itemView.findViewById(R.id.category_title)

    init {
        itemView.setOnClickListener(this)
    }

    fun onBind(recipe: Recipe) {
        categoryTitle.text = recipe.title
        val imageUri = Uri.parse("android.resource://com.anikinkirill.foodrecipes/drawable/${recipe.image_url}")
        requestManager.load(imageUri).into(categoryImage)
    }

    override fun onClick(v: View?) {
        onRecipeListener.onCategoryClick(categoryTitle.text.toString())
    }

}