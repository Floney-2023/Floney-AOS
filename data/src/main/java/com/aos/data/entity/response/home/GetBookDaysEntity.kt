package com.aos.data.entity.response.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetBookDaysEntity(
    val dayLiensResponse: List<DayLiensResponse> = listOf(),
    val totalExpense: List<TotalExpense>,
    val seeProfileImg: Boolean,
    val carryOverInfo: CarryOverInfoDays
)

@Serializable
data class DayLiensResponse(
    val id: Int,
    val money: Int,
    val description: String,
    val lineCategory: String,
    val lineSubCategory: String,
    val assetSubCategory: String,
    val exceptStatus: Boolean,
    val writerEmail: String,
    val writerNickName: String,
    val writerProfileImg: String,
)

@Serializable
data class TotalExpense(
    val money: Double,
    val categoryType: String
)


@Serializable
data class CarryOverInfoDays(
    val carryOverStatus: Boolean,
    val carryOverMoney: Double
)
