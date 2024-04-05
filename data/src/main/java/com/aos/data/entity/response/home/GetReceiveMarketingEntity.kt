package com.aos.data.entity.response.home

import kotlinx.serialization.Serializable

@Serializable
data class GetReceiveMarketingEntity(
    val agree: Boolean = false
)
