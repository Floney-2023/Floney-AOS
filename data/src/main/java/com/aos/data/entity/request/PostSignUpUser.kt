package com.aos.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class PostSignUpUser(
    val email: String,
    val nickname: String,
    val password: String,
    val receiveMarketing: String,
)
