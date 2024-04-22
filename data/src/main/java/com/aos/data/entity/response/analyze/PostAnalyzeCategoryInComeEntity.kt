package com.aos.data.entity.response.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeCategoryInComeEntity(
    val total: Double,
    val differance: Double,
    val analyzeResult: List<AnalyzeResult>
)
