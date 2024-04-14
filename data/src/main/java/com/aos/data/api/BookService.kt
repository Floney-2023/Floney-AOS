package com.aos.data.api

import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.request.book.PostBooksLinesBody
import com.aos.data.entity.request.book.PostBooksLinesEntity
import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    // 가계부 참여
    @POST("books/join")
    @Headers("Auth: true")
    suspend fun postBooksJoin(
        @Body postBooksJoinBody: PostBooksJoinBody
    ): NetworkState<PostBooksJoinEntity>

    // 가계부 생성 - 최초 가입 후 가계부 생성
    @POST("books")
    @Headers("Auth: true")
    suspend fun postBooksCreate(
        @Body postBooksCreateBody: PostBooksCreateBody
    ): NetworkState<PostBooksCreateEntity>

    // 카테고리 조회하기
    @GET("books/{bookKey}/categories")
    @Headers("Auth: true")
    suspend fun getBooksCategory(
        @Path("bookKey") bookKey: String,
        @Query("parent") parent: String
    ): NetworkState<List<GetBookCategoryEntity>>

    // 가계부 내역 추가
    @POST("books/lines")
    @Headers("Auth: true")
    suspend fun postBooksLines(
        @Body moneyData: PostBooksLinesBody
    ): NetworkState<PostBooksLinesEntity>

}