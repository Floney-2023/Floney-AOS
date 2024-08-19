package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksJoinBody(
    val code: String
)
