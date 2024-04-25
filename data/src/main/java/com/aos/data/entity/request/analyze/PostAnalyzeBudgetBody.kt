package com.aos.data.entity.request.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeBudgetBody (
    val bookKey: String,
    val date: String
)