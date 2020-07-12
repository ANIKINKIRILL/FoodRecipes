package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.adapters.OnRecipeListener
import com.anikinkirill.foodrecipes.adapters.RecipeRecyclerAdapter
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : BaseActivity(), OnRecipeListener {

    companion object {
        private const val TAG = "RecipeListActivity"
    }

    private lateinit var viewModel: RecipeListViewModel
    private lateinit var recyclerView: RecyclerView
    private val recipeAdapter = RecipeRecyclerAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        recyclerView = findViewById(R.id.recycler_view)
        initRecyclerView()

        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        viewModel.getRecipesList().observe(this, Observer<List<Recipe>> {
            recipeAdapter.setRecipes(it as ArrayList)
        })

        viewModel.searchRecipesApi("chicken", 1)

    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeListActivity)
            adapter = recipeAdapter
        }
    }

    override fun onRecipeClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCategoryClick(category: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
