package com.aos.data.api

import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BookService {

    // 유저 가계부 유효 확인
    @GET("books/users/check")
    @Headers("Auth: true")
    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>

    @GET("books/month")
    @Headers("Auth: true")
    suspend fun getBookMonth(
        @Query("bookKey") bookKey: String,
        @Query("date") date: String
    ): NetworkState<GetBookMonthEntity>

}