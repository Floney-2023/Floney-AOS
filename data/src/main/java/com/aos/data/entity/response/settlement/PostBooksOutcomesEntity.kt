package com.aos.data.entity.response.settlement

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksOutcomesEntity(
    val id: Int?,
    val money: Double,
    val category: List<String>,
    val assetType: String,
    val content: String,
    val img: String,
    val userEmail: String
)