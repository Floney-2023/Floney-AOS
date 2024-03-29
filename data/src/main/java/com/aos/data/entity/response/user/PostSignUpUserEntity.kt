package com.aos.data.entity.response.user

import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpUserEntity(
    val accessToken: String,
    val refreshToken: String
)
