package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.viewmodels.RecipeViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_recipe.*
import kotlin.math.roundToLong

class RecipeActivity : BaseActivity() {

    companion object {
        private const val TAG = "RecipeActivity"
    }

    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        showProgressBar(true)
        viewModel = ViewModelProviders.of(this).get(RecipeViewModel::class.java)
        subscribeObservers()
        getIntentExtra()
    }

    private fun subscribeObservers() {

    }

    private fun displayTimedOutRecipe() {
        Glide
            .with(this)
            .load(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(recipe_image)
        recipe_title.text = "Error with network connection"
    }

    private fun getIntentExtra() {

    }

    private fun setDataToWidgets(recipe: Recipe) {
        Glide
            .with(this)
            .load(recipe.image_url)
            .centerCrop()
            .into(recipe_image)

        recipe_title.text = recipe.title
        var ingredientsResultString = ""
        for(ingredient in recipe.ingredients!!){
            ingredientsResultString += (ingredient + "\n")
        }
        ingredients_container.text = ingredientsResultString
        recipe_social_score.text = recipe.social_rank?.roundToLong().toString()
        ingredients_title.text = "Ingredients"
    }

}