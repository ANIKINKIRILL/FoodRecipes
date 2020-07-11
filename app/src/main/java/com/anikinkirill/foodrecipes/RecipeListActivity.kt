package com.anikinkirill.foodrecipes

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : BaseActivity() {

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

    }
}
