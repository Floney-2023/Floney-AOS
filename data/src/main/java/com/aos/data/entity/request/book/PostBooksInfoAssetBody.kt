package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoAssetBody (
    val bookKey : String,
    val asset : Long
)