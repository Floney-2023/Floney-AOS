package com.aos.model.book


data class PostBookFavoriteModel(
    val id: Int,
    val money: Double,
    val description: String,
    val lineCategoryName : String,
    val lineSubcategoryName : String,
    val assetSubcategoryName : String,
    val exceptStatus : Boolean
)

