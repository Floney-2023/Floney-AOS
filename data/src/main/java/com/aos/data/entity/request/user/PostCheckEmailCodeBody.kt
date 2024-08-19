package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostCheckEmailCodeBody(
    val email: String,
    val code: String
)
