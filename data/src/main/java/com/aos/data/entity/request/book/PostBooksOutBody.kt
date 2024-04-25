package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable
import retrofit2.http.Query

@Serializable
data class PostBooksOutBody (
    val bookKey: String
)