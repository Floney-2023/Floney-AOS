package com.aos.repository

import com.aos.model.book.PostBooksJoinModel
import com.aos.model.home.GetCheckUserBookModel

interface BookRepository {

    // 유저 가계부 유효 확인
    suspend fun getCheckUserBook(): Result<GetCheckUserBookModel>

    // 가계부 참여
    suspend fun postBooksJoin(code : String): Result<PostBooksJoinModel>

}