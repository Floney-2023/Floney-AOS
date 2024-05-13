package com.aos.model.user

data class SocialUserModel(
    val provider: String,
    val token: String,
    val email: String,
    val nickname: String
)
