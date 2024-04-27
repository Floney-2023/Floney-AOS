package com.aos.data.entity.request.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeCategoryBody (
    val bookKey: String,
    val root: String,
    val date: String
)