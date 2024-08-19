package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostSocialSignUpUserBody(
    val email: String,
    val nickname: String,
    val receiveMarketing: Boolean,
)
