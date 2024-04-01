package com.aos.repository

import com.aos.model.home.GetCheckUserBookModel

interface BookRepository {

    // 유저 가계부 유효 확인
    suspend fun getCheckUserBook(): Result<GetCheckUserBookModel>

}