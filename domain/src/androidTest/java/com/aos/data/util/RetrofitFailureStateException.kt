package com.example.data.util

class RetrofitFailureStateException(error: String ?, val code: Int) : Exception(error) {
}