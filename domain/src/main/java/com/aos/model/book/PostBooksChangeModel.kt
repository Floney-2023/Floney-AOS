package com.aos.model.book

data class PostBooksChangeModel (
    val money: Double,
    val flow: String,
    val asset:String,
    val line: String,
    val lineDate: String,
    val description: String,
    val except: Boolean,
    val nickname: String,
)