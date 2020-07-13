package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        subscribeObservers()
        getIntentExtra()
    }

    private fun subscribeObservers() {
        viewModel.getRecipe().observe(this, Observer<Recipe> {
            if(it.recipe_id == viewModel.mRecipeId) {
                setDataToWidgets(it)
                showProgressBar(false)
                scrollView.visibility = View.VISIBLE
                viewModel.setDidRetrieveRecipe(true)
            }
        })

        viewModel.getNetworkTimeout().observe(this, Observer<Boolean> {
            if(it && !viewModel.getDidRetrieveRecipe()){
                Log.d(TAG, "subscribeObservers: timed out")
            }
        })
    }

    private fun getIntentExtra() {
        if(intent.hasExtra("recipe")){
            val recipe = intent.getParcelableExtra("recipe") as Recipe
            viewModel.getRecipeById(recipe.recipe_id!!)
        }
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