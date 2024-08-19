package com.aos.model.user

data class PostLoginModel(
    val accessToken: String,
    val refreshToken: String
)
