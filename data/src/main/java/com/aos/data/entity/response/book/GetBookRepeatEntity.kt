package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class GetBookRepeatEntity(
    val id: Int,
    val description : String,
    val repeatDuration : String,
    val lineSubCategory : String,
    val assetSubCategory : String,
    val money: Double
)