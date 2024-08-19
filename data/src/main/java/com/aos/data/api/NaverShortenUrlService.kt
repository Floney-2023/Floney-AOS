package com.aos.data.api

import com.aos.data.entity.response.settlement.PostNaverShortenUrlEntity
import com.aos.util.NetworkState
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface NaverShortenUrlService {
    @GET("shorturl")
    @Headers("Auth: false")
    suspend fun postShortenUrl(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("url") url: String
    )
    : NetworkState<PostNaverShortenUrlEntity>

}