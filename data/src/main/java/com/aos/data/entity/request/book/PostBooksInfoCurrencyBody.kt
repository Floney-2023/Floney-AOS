package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoCurrencyBody (
    val requestCurrency : String,
    val bookKey: String
)