package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoCarryOverBody (
    val status : Boolean,
    val bookKey : String
)