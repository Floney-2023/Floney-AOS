package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class DeleteWithdrawBody(
    val type: String,
    val reason: String?
)
