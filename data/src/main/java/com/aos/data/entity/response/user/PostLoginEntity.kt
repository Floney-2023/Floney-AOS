package com.aos.data.entity.response.user

import kotlinx.serialization.Serializable

@Serializable
data class PostLoginEntity(
    val accessToken: String,
    val refreshToken: String
)
