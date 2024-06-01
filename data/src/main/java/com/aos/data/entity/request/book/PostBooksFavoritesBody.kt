package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksFavoritesBody (
    val money: Double,
    val description: String,
    val lineCategoryName: String,
    val lineSubcategoryName: String,
    val assetSubcategoryName: String,
    val exceptStatus : Boolean
)