package com.anikinkirill.foodrecipes

sealed class ViewState {

    object CATEGORIES : ViewState()
    object RECIPES : ViewState()

}