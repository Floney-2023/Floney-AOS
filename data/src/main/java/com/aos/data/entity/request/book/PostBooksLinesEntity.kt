package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksLinesEntity(
    val money: Int,
    val flow: String,
    val asset:String,
    val line: String,
    val lineDate: String,
    val description: String,
    val except: String,
    val nickname: String,
    val repeatDuration: String
)