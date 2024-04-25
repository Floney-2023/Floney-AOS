package com.aos.data.entity.response.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeBudgetEntity(
    val leftMoney: Double,
    val initBudget: Double
)
