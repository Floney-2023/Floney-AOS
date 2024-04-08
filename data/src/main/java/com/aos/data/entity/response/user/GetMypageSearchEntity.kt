package com.aos.data.entity.response.user

import kotlinx.serialization.Serializable

@Serializable
data class GetMypageSearchEntity(
    val nickname: String,
    val email: String,
    val profileImg: String,
    val provider: String,
    val lastAdTime: String,
    val myBooks: List<MyBooks>
)
@Serializable
data class MyBooks(
    val bookImg: String?,
    val bookKey: String,
    val name: String,
    val memberCount: Int
)

