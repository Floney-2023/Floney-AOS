package com.aos.data.entity.response.home

import kotlinx.serialization.Serializable

@Serializable
data class GetCheckUserBookEntity(
    val bookKey: String? = ""
)
