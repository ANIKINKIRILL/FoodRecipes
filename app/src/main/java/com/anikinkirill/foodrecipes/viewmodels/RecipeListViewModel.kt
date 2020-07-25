package com.anikinkirill.foodrecipes.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.anikinkirill.foodrecipes.ViewState
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository
import com.anikinkirill.foodrecipes.util.Resource

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "RecipeListViewModel"
        const val QUERY_EXHAUSTED = "No more results"
    }

    private val mViewState = MutableLiveData<ViewState>()
    private val repository = RecipeRepository.getInstance(application)
    private val recipes = MediatorLiveData<Resource<List<Recipe>>>()

    // query extras
    private var isQueryExhausted: Boolean = false
    private var isPerformingQuery: Boolean = false
    private var page: Int? = null
    private var query: String? = null

    init {
        mViewState.value = ViewState.CATEGORIES
    }

    fun viewState() = mViewState

    fun searchRecipes(query: String, page: Int) {
        var pageNumber = page
        if(!isPerformingQuery) {
            if(page == 0) {
                pageNumber = 1
            }
            this.page = pageNumber
            this.query = query
            isQueryExhausted = false
            executeSearch()
        }
    }

    private fun executeSearch() {
        isPerformingQuery = true
        mViewState.value = ViewState.RECIPES
        val repositorySource = repository.searchRecipesApi(query!!, page!!)
        recipes.addSource(repositorySource) {
            if(it != null) {
                recipes.value = it
                if(it.status == Resource.Status.SUCCESS) {
                    isPerformingQuery = false
                    if(it.data != null) {
                        if(it.data.isEmpty()) {
                            Log.d(TAG, "executeSearch: query is exhausted")
                            recipes.value = Resource.Error(it.data, QUERY_EXHAUSTED)
                        }
                    }
                    recipes.removeSource(repositorySource)
                }else if(it.status == Resource.Status.ERROR){
                    isPerformingQuery = false
                    recipes.removeSource(repositorySource)
                }
            }else {
                recipes.removeSource(repositorySource)
            }
        }
    }

    fun getPageNumber() = page

    fun getRecipes() : LiveData<Resource<List<Recipe>>> {
        return recipes
    }

}