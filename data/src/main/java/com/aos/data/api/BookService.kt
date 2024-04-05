package com.aos.data.api

import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookService {

    // 유저 가계부 유효 확인
    @GET("books/users/check")
    @Headers("Auth: true")
    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>

    // 캘린더 조회 (월별)
    @GET("books/month")
    @Headers("Auth: true")
    suspend fun getBookMonth(
        @Query("bookKey") bookKey: String,
        @Query("date") date: String
    ): NetworkState<GetBookMonthEntity>


    // 일별 조회
    @GET("books/days")
    @Headers("Auth: true")
    suspend fun getBookDays(
        @Query("bookKey") bookKey: String,
        @Query("date") date: String
    ): NetworkState<GetBookDaysEntity>

    @GET("books/info")
    @Headers("Auth: true")
    suspend fun getBookInfo(
        @Query("bookKey") bookKey: String,
    ): NetworkState<GetBookInfoEntity>

}