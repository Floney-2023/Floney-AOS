package com.aos.model.book

import androidx.recyclerview.widget.DiffUtil


data class UiBookFavoriteModel(
    val idx: Int,
    var checked: Boolean = false,
    val description: String,
    val lineCategoryName : String,
    val lineSubcategoryName : String,
    val assetSubcategoryName : String,
    val money: String,
    val exceptStatus : Boolean
){
    interface OnItemClickListener {
        fun onItemClick(item: UiBookFavoriteModel)
    }

    companion object : DiffUtil.ItemCallback<UiBookFavoriteModel>() {
        override fun areItemsTheSame(oldItem: UiBookFavoriteModel, newItem: UiBookFavoriteModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: UiBookFavoriteModel, newItem: UiBookFavoriteModel): Boolean {
            return oldItem == newItem
        }
    }
}

