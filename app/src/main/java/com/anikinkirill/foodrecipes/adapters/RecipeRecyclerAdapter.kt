package com.anikinkirill.foodrecipes.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.Constants
import com.bumptech.glide.RequestManager

private const val RECIPE_VIEW_HOLDER = 1
private const val LOADING_VIEW_HOLDER = 2
private const val CATEGORY_VIEW_HOLDER = 3
private const val EXHAUSTED_VIEW_HOLDER = 4

class RecipeRecyclerAdapter(
    private val onRecipeListener: OnRecipeListener,
    private val requestManager: RequestManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "RecipeRecyclerAdapter"
    }

    private var recipes = arrayListOf<Recipe>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RECIPE_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
                RecipeViewHolder(view, onRecipeListener, requestManager)
            }
            LOADING_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_list_item, parent, false)
                LoadingViewHolder(view)
            }
            CATEGORY_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
                CategoryViewHolder(view, onRecipeListener, requestManager)
            }
            EXHAUSTED_VIEW_HOLDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_search_exhausted, parent, false)
                ExhaustedViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
                RecipeViewHolder(view, onRecipeListener, requestManager)
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
            viewHolder.onBind(recipe)
        }else if(getItemViewType(position) == CATEGORY_VIEW_HOLDER) {
            val viewHolder = holder as CategoryViewHolder
            viewHolder.onBind(recipe)
        }

    }

    fun setRecipes(newRecipes: ArrayList<Recipe>){
        recipes = newRecipes
        notifyDataSetChanged()
    }

    // for searching recipes
    fun displayOnlyLoading() {
        Log.d(TAG, "displayOnlyLoading: called")
        clearRecipesList()
        val recipe = Recipe().apply { title = "LOADING..." }
        recipes.add(recipe)
        notifyDataSetChanged()
    }

    private fun clearRecipesList() {
        recipes.clear()
        notifyDataSetChanged()
    }

    // for pagination
    fun displayLoading() {
        if(!isLoading()) {
            val recipe = Recipe().apply { title = "LOADING..." }
            recipes.add(recipe)
            notifyDataSetChanged()
        }
    }

    private fun isLoading() : Boolean {
        if(recipes.size > 0) {
            if (recipes[recipes.lastIndex].title == "LOADING...") {
                Log.d(TAG, "isLoading: true")
                return true
            }
        }
        Log.d(TAG, "isLoading: false")
        return false
    }

    fun hideLoading() {
        if(isLoading()) {
            if(recipes.first().title == "LOADING..."){
                recipes.removeAt(0)
            } else if(recipes.last().title == "LOADING..."){
                recipes.removeAt(recipes.size - 1)
            }
            notifyDataSetChanged()
        }
    }

    fun displaySearchCategories() {
        val categoriesList = arrayListOf<Recipe>()
        for(i in Constants.DEFAULT_SEARCH_CATEGORIES.indices){
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

    fun displayExhaustedSearch() {
        // hide loading process
        hideLoading()
        val exhaustedRecipe = Recipe().apply { title = "EXHAUSTED..." }
        recipes.add(exhaustedRecipe)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            recipes[position].social_rank?.toInt() == -1 -> {
                Log.d(TAG, "getItemViewType: CATEGORY")
                CATEGORY_VIEW_HOLDER
            }
            recipes[position].title == "LOADING..." -> {
                Log.d(TAG, "getItemViewType: LOADING")
                LOADING_VIEW_HOLDER
            }
            recipes[position].title == "EXHAUSTED..." -> {
                Log.d(TAG, "getItemViewType: EXHAUSTED")
                EXHAUSTED_VIEW_HOLDER
            }
            else -> {
                Log.d(TAG, "getItemViewType: RECIPE")
                RECIPE_VIEW_HOLDER
            }
        }
    }

    fun getRecipes() = recipes

}