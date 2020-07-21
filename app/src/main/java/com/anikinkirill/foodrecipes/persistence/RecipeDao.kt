package com.anikinkirill.foodrecipes.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.anikinkirill.foodrecipes.models.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = IGNORE)
    fun insertRecipes(vararg recipe : Recipe) : LongArray

    @Insert(onConflict = REPLACE)
    fun insertRecipe(recipe: Recipe)

    @Query("UPDATE recipes SET title = :title, publisher = :publisher, image_url = :image_url, social_rank = :social_rank WHERE recipe_id = :recipe_id ")
    fun updateRecipe(recipe_id : String, title : String, publisher : String, image_url : String, social_rank : Float)

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%' ORDER BY social_rank DESC LIMIT (:pageNumber * 30)")
    fun searchRecipes(query : String, pageNumber : Int) : LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipe_id")
    fun getRecipe(recipe_id: String) : LiveData<Recipe>

}