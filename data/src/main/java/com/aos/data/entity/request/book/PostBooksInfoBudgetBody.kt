package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoBudgetBody (
    val bookKey : String,
    val budget : Int,
    val date : String
)