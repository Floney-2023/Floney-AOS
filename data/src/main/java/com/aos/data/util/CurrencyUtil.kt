package com.aos.data.util

import com.aos.model.book.CurrencyInform
import timber.log.Timber
import javax.inject.Inject

object CurrencyUtil {
    var currency = ""
}

fun getCurrencyCodeBySymbol(symbol: String): String {
    val currencyList = CurrencyInform()
    val currency = currencyList.find { it.symbol == symbol }
    return currency?.code ?: ""
}

fun checkDecimalPoint(): Boolean {
    val currencyCode = getCurrencyCodeBySymbol(CurrencyUtil.currency)
    val noDecimalCurrencies = setOf("KRW", "JPY", "CNY", "VND")
    return !noDecimalCurrencies.contains(currencyCode)
}