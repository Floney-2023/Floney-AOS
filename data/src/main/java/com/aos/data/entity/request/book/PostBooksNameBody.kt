package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksNameBody (
    val name: String,
    val bookKey : String
)