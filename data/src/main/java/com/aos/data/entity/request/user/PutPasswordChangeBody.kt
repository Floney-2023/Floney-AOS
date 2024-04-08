package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PutPasswordChangeBody(
    val newPassword: String,
    val oldPassword: String
)
