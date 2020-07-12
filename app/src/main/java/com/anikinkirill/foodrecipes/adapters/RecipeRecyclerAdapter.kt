package com.anikinkirill.foodrecipes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import com.anikinkirill.foodrecipes.models.Recipe
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class RecipeRecyclerAdapter(
    private val onRecipeListener: OnRecipeListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recipes = arrayListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return RecipeViewHolder(
            view,
            onRecipeListener
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recipe = recipes[position]
        val viewHolder = holder as RecipeViewHolder
        viewHolder.title.text = recipe.title
        viewHolder.publisher.text = recipe.publisher
        viewHolder.social_score.text = recipe.social_rank?.roundToInt().toString()

        Glide
            .with(viewHolder.itemView)
            .load(recipe.image_url)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewHolder.image)

    }

    fun setRecipes(newRecipes: ArrayList<Recipe>){
        recipes = newRecipes
        notifyDataSetChanged()
    }

}