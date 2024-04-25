package com.aos.data.entity.response.settlement

import kotlinx.serialization.Serializable

@Serializable
data class PostSettlementAddEntity(
    val id: Long,
    val startDate : String,
    val endDate : String,
    val userCount : Int,
    val totalOutcome : Double,
    val outcome : Double,
    val details : List<Details>
)

@Serializable
data class Details(
    val money: Double,
    val userNickname: String,
    val userProfileImg: String
)