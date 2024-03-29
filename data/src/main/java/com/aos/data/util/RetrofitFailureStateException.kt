package com.aos.data.util

class RetrofitFailureStateException(error: String ?, val code: Int) : Exception(error) {
}