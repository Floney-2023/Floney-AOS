package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpUserBody(
    val email: String,
    val nickname: String,
    val password: String,
    val receiveMarketing: Boolean,
)
