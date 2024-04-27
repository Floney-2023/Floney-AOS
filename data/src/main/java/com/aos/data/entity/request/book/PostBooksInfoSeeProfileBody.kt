package com.aos.data.entity.request.book

import kotlinx.serialization.Serializable

@Serializable
data class PostBooksInfoSeeProfileBody (
    val bookKey: String,
    val seeProfileStatus : Boolean
)