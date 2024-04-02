package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksJoinEntity(
    val bookKey: String,
    val code: String
)
