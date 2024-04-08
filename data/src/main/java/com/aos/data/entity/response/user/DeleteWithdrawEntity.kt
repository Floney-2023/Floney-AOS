package com.aos.data.entity.response.user

import kotlinx.serialization.Serializable

@Serializable
data class DeleteWithdrawEntity(
    val deletedBookKeys: List<String>,
    val otherBookKeys: List<String>
)
