package com.anikinkirill.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import com.anikinkirill.foodrecipes.models.Recipe
import com.bumptech.glide.RequestManager
import kotlin.math.roundToInt

class RecipeViewHolder(
    itemView: View,
    private val onRecipeListener: OnRecipeListener,
    private val requestManager: RequestManager
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var title: TextView = itemView.findViewById(R.id.recipe_title)
    var publisher: TextView = itemView.findViewById(R.id.recipe_publisher)
    var social_score: TextView = itemView.findViewById(R.id.recipe_social_score)
    var image: AppCompatImageView = itemView.findViewById(R.id.recipe_image)

    init {
        itemView.setOnClickListener(this)
    }

    fun onBind(recipe: Recipe) {
        title.text = recipe.title
        publisher.text = recipe.publisher
        social_score.text = recipe.social_rank?.roundToInt().toString()
        requestManager.load(recipe.image_url).into(image)
    }

    override fun onClick(v: View?) {
        onRecipeListener.onRecipeClick(adapterPosition)
    }


}