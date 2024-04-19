package com.aos.data.entity.response.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeCategoryOutComeEntity(
    val total: Double,
    val differance: Double,
    val analyzeResult: List<AnalyzeResult>
)

@Serializable
data class AnalyzeResult(
    val category: String,
    val money: Double
)