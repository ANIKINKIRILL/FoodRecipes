package com.anikinkirill.foodrecipes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.anikinkirill.foodrecipes.ViewState
import com.anikinkirill.foodrecipes.models.Recipe
import com.anikinkirill.foodrecipes.repositories.RecipeRepository
import com.anikinkirill.foodrecipes.util.Resource

class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val mViewState = MutableLiveData<ViewState>()
    private val repository = RecipeRepository(application)
    private val recipes = MediatorLiveData<Resource<List<Recipe>>>()

    init {
        mViewState.value = ViewState.CATEGORIES
    }

    fun viewState() = mViewState

    fun searchRecipes(query: String, page: Int) {
        val repositorySource = repository.searchRecipesApi(query, page)
        recipes.addSource(repositorySource) {
            recipes.value = it
        }
    }

    fun getRecipes() : LiveData<Resource<List<Recipe>>> {
        return recipes
    }

}