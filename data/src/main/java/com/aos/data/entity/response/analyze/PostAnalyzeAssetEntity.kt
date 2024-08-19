package com.aos.data.entity.response.analyze

import kotlinx.serialization.Serializable

@Serializable
data class PostAnalyzeAssetEntity(
    val difference: Double,
    val initAsset: Double,
    val currentAsset: Double,
    val assetInfo: Map<String, Double>
)