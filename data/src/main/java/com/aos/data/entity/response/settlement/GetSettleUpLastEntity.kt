package com.aos.data.entity.response.settlement

import kotlinx.serialization.Serializable

@Serializable
data class GetSettleUpLastEntity(
    val passedDays: Long
)
