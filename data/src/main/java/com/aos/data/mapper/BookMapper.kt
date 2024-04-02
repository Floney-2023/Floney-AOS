package com.aos.data.mapper

import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.home.GetCheckUserBookModel

fun GetCheckUserBookEntity.toGetCheckUserBookModel(): GetCheckUserBookModel {
    return GetCheckUserBookModel(this.bookKey ?: "")
}

fun PostBooksJoinEntity.toPostBooksJoinModel(): PostBooksJoinModel {
    return PostBooksJoinModel(this.bookKey ?: "", this.code?: "")
}