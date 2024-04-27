package com.aos.data.entity.request.settlement

import kotlinx.serialization.Serializable

@Serializable
data class PostSettlementAddBody(
    val bookKey: String,
    val startDate : String,
    val endDate : String,
    val userEmails: List<String>,
    val outcomes: List<Outcomes>,
)
@Serializable
data class Outcomes(
    val outcome: Double,
    val userEmail: String
)