package com.aos.repository

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.PostBooksLinesModel
import com.aos.model.book.UiBookCategory
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.home.UiBookMonthModel

interface BookRepository {

    // 유저 가계부 유효 확인
    suspend fun getCheckUserBook(): Result<GetCheckUserBookModel>

    // 캘린더 조회
    suspend fun getBooksMonth(bookKey: String, date: String): Result<UiBookMonthModel>

    // 일별 내역 조회
    suspend fun getBooksDays(bookKey: String, date: String): Result<UiBookDayModel>

    // 가계부 정보 조회
    suspend fun getBookInfo(bookKey: String): Result<UiBookInfoModel>

    // 가계부 참여
    suspend fun postBooksJoin(code: String): Result<PostBooksJoinModel>

    // 가계부 생성
    suspend fun postBooksCreate(name: String, profileImg: String): Result<PostBooksCreateModel>

    // 가계부 생성
    suspend fun getBookCategory(bookKey: String, parent: String): Result<List<UiBookCategory>>

    // 가계부 내역 추가
    suspend fun postBooksLines(
        bookKey: String,
        money: Int,
        flow: String,
        asset: String,
        line: String,
        lineDate: String,
        description: String,
        except: Boolean,
        nickname: String,
        repeatDuration: String
    ): Result<PostBooksLinesModel>

}