package com.anikinkirill.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anikinkirill.foodrecipes.R
import de.hdodenhof.circleimageview.CircleImageView

class CategoryViewHolder(
    itemView: View,
    private val onRecipeListener: OnRecipeListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var categoryImage: CircleImageView = itemView.findViewById(R.id.category_image)
    var categoryTitle: TextView = itemView.findViewById(R.id.category_title)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecipeListener.onCategoryClick(categoryTitle.text.toString())
    }

}