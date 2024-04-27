package com.aos.data.entity.response.book

import kotlinx.serialization.Serializable

@Serializable
data class GetBooksInfoCurrencyEntity (
    val myBookCurrency: String
)