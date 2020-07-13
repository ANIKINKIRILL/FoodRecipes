package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import com.anikinkirill.foodrecipes.models.Recipe

class RecipeActivity : BaseActivity() {

    companion object {
        private const val TAG = "RecipeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        getIntentExtra()
    }

    private fun getIntentExtra() {
        if(intent.hasExtra("recipe")){
            val recipe = intent.getParcelableExtra("recipe") as Recipe
            Log.d(TAG, "getIntentExtra: ${recipe.recipe_id}")
        }
    }

}