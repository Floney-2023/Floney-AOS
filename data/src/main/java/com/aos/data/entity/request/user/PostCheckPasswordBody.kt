package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostCheckPasswordBody(
    val password: String
)
