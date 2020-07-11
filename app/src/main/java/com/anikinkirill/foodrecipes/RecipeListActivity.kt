package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.util.Log
import android.view.View
import com.anikinkirill.foodrecipes.requests.ServiceGenerator
import com.anikinkirill.foodrecipes.responses.RecipeResponse
import com.anikinkirill.foodrecipes.util.Constants
import kotlinx.android.synthetic.main.activity_recipe_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListActivity : BaseActivity() {

    companion object {
        private const val TAG = "RecipeListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        testProgressBarButton.setOnClickListener {
            if(progressBar.visibility == View.VISIBLE){
                showProgressBar(false)
            }else{
                showProgressBar(true)
            }
        }

        testRecipeResponses()

    }

    private fun testRecipeResponses() {
        val recipeApi = ServiceGenerator.recipeApi
        val callRecipeApi = recipeApi.getRecipeById(Constants.API_KEY, 35109)
        callRecipeApi.enqueue(object: Callback<RecipeResponse> {
            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                Log.d(TAG, "onResponse: ${response.body()?.recipe?.title}")
            }
        })
    }

}
