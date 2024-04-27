package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class DeleteBookCategoryBody(
    val parent: String,
    val name : String
)
