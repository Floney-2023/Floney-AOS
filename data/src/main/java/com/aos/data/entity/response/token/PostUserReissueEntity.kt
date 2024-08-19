package com.aos.data.entity.response.token

import kotlinx.serialization.Serializable

@Serializable
data class PostUserReissueEntity (
    val accessToken: String,
    val refreshToken: String
)