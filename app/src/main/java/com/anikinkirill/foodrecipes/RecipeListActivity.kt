package com.anikinkirill.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.adapters.OnRecipeListener
import com.anikinkirill.foodrecipes.adapters.RecipeRecyclerAdapter
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.RecipeRecyclerItemDecoration
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

        setSupportActionBar(findViewById(R.id.toolbar))

        recyclerView = findViewById(R.id.recycler_view)
        initRecyclerView()

        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        viewModel.getRecipesList().observe(this, Observer<List<Recipe>> {
            viewModel.setIsPerformingRequest(false)
            recipeAdapter.setRecipes(it as ArrayList)
        })
        
        viewModel.isQueryExhausted().observe(this, Observer {
            if(it) {
                Log.d(TAG, "onCreate: should be exhausted")
                recipeAdapter.displayExhaustedSearch()
            }
        })
        
        initSearchView()

        if(!viewModel.isViewingRecipes()){
            displaySearchCategories()
        }

    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeListActivity)
            adapter = recipeAdapter
            addItemDecoration(RecipeRecyclerItemDecoration(30))
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recyclerView.canScrollVertically(1)){
                    viewModel.searchNextPage()
                }
            }
        })
    }

    private fun initSearchView() {
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val userQuery: String = query ?: "chicken"
                recipeAdapter.displayLoading()
                viewModel.searchRecipesApi(userQuery, 1)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onRecipeClick(position: Int) {
        val recipe = recipeAdapter.getRecipes()[position]
        val intent = Intent(this, RecipeActivity::class.java).apply {
            this.putExtra("recipe", recipe)
        }
        startActivity(intent)
    }

    override fun onCategoryClick(category: String) {
        viewModel.searchRecipesApi(category, 1)
    }

    private fun displaySearchCategories(){
        viewModel.setIsViewingRecipes(false)
        recipeAdapter.displaySearchCategories()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_categories -> displaySearchCategories()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(viewModel.onBackPressed()){
            super.onBackPressed()
        }else{
            displaySearchCategories()
        }
    }

}
