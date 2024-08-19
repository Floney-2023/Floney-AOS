package com.aos.data.entity.request.user

import kotlinx.serialization.Serializable

@Serializable
data class PostRecentBookkeySaveBody(
    val bookKey: String
)
