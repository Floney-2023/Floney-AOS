package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostLoginBody(
    val email: String,
    val password: String
)
