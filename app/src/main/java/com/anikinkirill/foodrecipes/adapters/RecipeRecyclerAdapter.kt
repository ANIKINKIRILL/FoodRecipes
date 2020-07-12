package com.anikinkirill.foodrecipes.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.Constants
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

private const val RECIPE_VIEW_HOLDER = 1
private const val LOADING_VIEW_HOLDER = 2
private const val CATEGORY_VIEW_HOLDER = 3

class RecipeRecyclerAdapter(
    private val onRecipeListener: OnRecipeListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var recipes = arrayListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RECIPE_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
                RecipeViewHolder(view, onRecipeListener)
            }
            LOADING_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_list_item, parent, false)
                LoadingViewHolder(view)
            }
            CATEGORY_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
                CategoryViewHolder(view, onRecipeListener)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
                RecipeViewHolder(view, onRecipeListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recipe = recipes[position]
        if(getItemViewType(position) == RECIPE_VIEW_HOLDER) {
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
        }else if(getItemViewType(position) == CATEGORY_VIEW_HOLDER) {
            val viewHolder = holder as CategoryViewHolder
            viewHolder.categoryTitle.text = recipe.title

            val imageUri = Uri.parse("android.resource://com.anikinkirill.foodrecipes/drawable/${recipe.image_url}")

            Glide
                .with(viewHolder.itemView)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.categoryImage)
        }

    }

    fun setRecipes(newRecipes: ArrayList<Recipe>){
        recipes = newRecipes
        notifyDataSetChanged()
    }

    fun displayLoading() {
        if(!isLoading()){
            val loadingRecipe = Recipe().apply { title = "LOADING..." }
            val loadingRecipesList = arrayListOf(loadingRecipe)
            recipes = loadingRecipesList
            notifyDataSetChanged()
        }
    }

    private fun isLoading() : Boolean {
        if(recipes.size > 0) {
            if (recipes[recipes.lastIndex].title == "LOADING...") {
                return true
            }
        }
        return false
    }

    fun displaySearchCategories() {
        val categoriesList = arrayListOf<Recipe>()
        for(i in 0..Constants.DEFAULT_SEARCH_CATEGORIES.size){
            val recipe = Recipe().apply {
                title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
                image_url = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
                social_rank = -1f
            }
            categoriesList.add(recipe)
        }
        recipes = categoriesList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            recipes[position].social_rank?.toInt() == -1 -> CATEGORY_VIEW_HOLDER
            recipes[position].title == "LOADING..." -> LOADING_VIEW_HOLDER
            else -> RECIPE_VIEW_HOLDER
        }
    }

}