package com.aos.data.mapper

import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.model.home.GetCheckUserBookModel

fun GetCheckUserBookEntity.toGetCheckUserBookModel(): GetCheckUserBookModel {
    return GetCheckUserBookModel(this.bookKey ?: "")
}