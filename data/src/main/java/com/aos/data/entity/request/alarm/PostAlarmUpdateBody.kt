package com.aos.data.entity.request.alarm

import kotlinx.serialization.Serializable

@Serializable
data class PostAlarmUpdateBody (
    val id: Int,
    val bookKey: String
)