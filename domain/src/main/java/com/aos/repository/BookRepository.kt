package com.aos.repository

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookMonthModel

interface BookRepository {

    // 유저 가계부 유효 확인
    suspend fun getCheckUserBook(): Result<GetCheckUserBookModel>

    // 캘린더 조회
    suspend fun getBooksMonth(bookKey: String, date: String): Result<UiBookMonthModel>

}