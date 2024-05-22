package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostChangeBookImgBody(
    val newUrl: String,
    val bookKey: String
)