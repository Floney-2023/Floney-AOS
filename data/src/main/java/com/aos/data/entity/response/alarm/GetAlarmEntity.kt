package com.aos.data.entity.response.alarm

import kotlinx.serialization.Serializable

@Serializable
data class GetAlarmEntity(
    val id: Int,
    val title: String,
    val body: String,
    val imgUrl: String,
    val date: String,
    val received: Boolean
)
