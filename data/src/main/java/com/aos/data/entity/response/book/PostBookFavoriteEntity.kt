package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBookFavoriteEntity(
    val id: Int,
    val money: Double,
    val description: String,
    val lineCategoryName: String,
    val lineSubcategoryName: String,
    val assetSubcategoryName: String
)
