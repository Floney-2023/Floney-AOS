package com.aos.data.entity.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBookMonthEntity(
    val expenses: List<Expenses>,
    val totalIncome: Double,
    val totalOutcome: Double = 0.0,
    val carryOverInfo: CarryOverInfo
)

@Serializable
data class Expenses(
    val date: String,
    val money: Double,
    val categoryType: String
)

@Serializable
data class CarryOverInfo(
    val carryOverStatus: Boolean,
    val carryOverMoney: Double
)
