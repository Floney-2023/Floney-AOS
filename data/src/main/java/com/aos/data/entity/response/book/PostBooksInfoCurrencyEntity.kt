package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoCurrencyEntity (
    val myBookCurrency: String
)