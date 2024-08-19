package com.aos.data.entity.request.settlement

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksOutcomesBody(
    val usersEmails: List<String>,
    val duration: Duration,
    val bookKey: String
)
@Serializable
data class Duration(
    val startDate: String,
    val endDate: String
)