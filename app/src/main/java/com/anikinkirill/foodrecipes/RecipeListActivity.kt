package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : BaseActivity() {

    companion object {
        private const val TAG = "RecipeListActivity"
    }

    private lateinit var viewModel: RecipeListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        viewModel.getRecipesList().observe(this, Observer<List<Recipe>> {
            Log.d(TAG, "onCreate: ${it.size}")
        })

        testQuery.setOnClickListener {
            viewModel.searchRecipesApi("bacon", 1)
        }

    }
}
