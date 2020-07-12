package com.anikinkirill.foodrecipes.util

class Constants {

    companion object {

        const val API_KEY = "" // this is blanked, because of the new api "https://recipesapi.herokuapp.com/"
        const val BASE_URL = "https://recipesapi.herokuapp.com"
        const val NETWORK_TIMEOUT = 3000    // 3 seconds

        val DEFAULT_SEARCH_CATEGORIES = arrayOf(
            "Barbeque",
            "Breakfast",
            "Chicken",
            "Beef",
            "Brunch",
            "Dinner",
            "Wine",
            "Italian"
        )

        val DEFAULT_SEARCH_CATEGORY_IMAGES = arrayOf(
            "barbeque",
            "breakfast",
            "chicken",
            "beef",
            "brunch",
            "dinner",
            "wine",
            "italian"
        )

    }

}