package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GetBooksBudgetEntity(
    @SerialName("JANUARY") val january: Double,
    @SerialName("FEBRUARY") val february: Double,
    @SerialName("MARCH") val march: Double,
    @SerialName("APRIL") val april: Double,
    @SerialName("MAY") val may: Double,
    @SerialName("JUNE") val june: Double,
    @SerialName("JULY") val july: Double,
    @SerialName("AUGUST") val august: Double,
    @SerialName("SEPTEMBER") val september: Double,
    @SerialName("OCTOBER") val october: Double,
    @SerialName("NOVEMBER") val november: Double,
    @SerialName("DECEMBER") val december: Double
)