package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class GetBooksCodeEntity (
    val code: String
)