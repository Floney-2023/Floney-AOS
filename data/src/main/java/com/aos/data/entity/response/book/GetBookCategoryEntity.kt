package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class GetBookCategoryEntity(
    val name: String,
    val default: Boolean
)

@Serializable
data class GetBookCategoryItem(
    val name: String,
    val default: Boolean
)
