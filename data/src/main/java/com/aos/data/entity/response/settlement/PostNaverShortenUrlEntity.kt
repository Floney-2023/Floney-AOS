package com.aos.data.entity.response.settlement

import kotlinx.serialization.Serializable

@Serializable
data class PostNaverShortenUrlEntity(
    val result: NaverResult,
    val message: String,
    val code: String
)
@Serializable
data class NaverResult(
    val url: String,
    val hash: String,
    val orgUrl : String
)