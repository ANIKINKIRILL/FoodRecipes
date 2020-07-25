package com.anikinkirill.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.adapters.OnRecipeListener
import com.anikinkirill.foodrecipes.adapters.RecipeRecyclerAdapter
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.util.RecipeRecyclerItemDecoration
import com.anikinkirill.foodrecipes.util.Testing
import com.anikinkirill.foodrecipes.viewmodels.RecipeListViewModel

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

        initRecyclerView()

        viewModel = ViewModelProviders.of(this).get(RecipeListViewModel::class.java)
        
        initSearchView()

        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.getRecipes().observe(this, Observer {
            it?.let {
                Log.d(TAG, "subscribeObservers: STATUS : ${it.status}")
                it.data?.let { data ->
                    recipeAdapter.setRecipes(data as ArrayList<Recipe>)
                }
            }
        })

        viewModel.viewState().observe(this, Observer {
            when(it){
                is ViewState.RECIPES -> {
                    // display recipes will trigger automatically from another observer
                }
                is ViewState.CATEGORIES -> {
                    displaySearchCategories()
                }
            }
        })
    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeListActivity)
            adapter = recipeAdapter
            addItemDecoration(RecipeRecyclerItemDecoration(30))
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recyclerView.canScrollVertically(1)){

                }
            }
        })
    }

    private fun initSearchView() {
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchRecipes(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchRecipes(query: String) {
        viewModel.searchRecipes(query, 1)
    }

    override fun onRecipeClick(position: Int) {
        val recipe = recipeAdapter.getRecipes()[position]
        val intent = Intent(this, RecipeActivity::class.java).apply {
            this.putExtra("recipe", recipe)
        }
        startActivity(intent)
    }

    override fun onCategoryClick(category: String) {
        searchRecipes(category)
    }

    private fun displaySearchCategories(){
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

}
