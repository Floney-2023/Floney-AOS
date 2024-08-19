package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoBudgetBody (
    val bookKey : String,
    val budget : Long,
    val date : String
)