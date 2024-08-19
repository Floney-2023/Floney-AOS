package com.aos.data.entity.request.alarm

import kotlinx.serialization.Serializable

@Serializable
data class PostAlarmSaveBody (
    val bookKey: String,
    val title: String,
    val body: String,
    val imgUrl: String,
    val userEmail: String,
    val date: String
)