package com.aos.data.entity.response.settlement

import kotlinx.serialization.Serializable

@Serializable
data class GetSettlementSeeEntity(
    val id: Long,
    val startDate: String,
    val endDate : String,
    val userCount : Int,
    val totalOutcome : Double,
    val outcome : Double
)