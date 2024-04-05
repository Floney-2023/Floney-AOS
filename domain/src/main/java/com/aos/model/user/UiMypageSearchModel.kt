package com.aos.model.user

data class UiMypageSearchModel(
    val nickname: String,
    val email: String,
    val profileImg: String,
    val provider: String,
    val lastAdTime: String,
    val myBooks: List<MyBooks>
)
data class MyBooks(
    val bookImg: String?,
    val bookKey: String,
    val name: String,
    val memberCount: Int
)