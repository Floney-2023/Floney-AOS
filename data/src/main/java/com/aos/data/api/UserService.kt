package com.aos.data.api

import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PostLoginBody
import com.aos.data.entity.request.user.PostSignUpUserBody
import com.aos.data.entity.response.user.PostLoginEntity
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    // 회원가입
    @POST("users")
    @Headers("Auth: false")
    suspend fun postSignUpUser( 
        @Body postSignUpUserBody: PostSignUpUserBody
    ): NetworkState<PostSignUpUserEntity>

    // 이메일 전송
    @GET("users/email/mail")
    @Headers("Auth: false")
    suspend fun getSendEmail(
        @Query("email") email: String
    ): NetworkState<Void>

    // 이메일 코드 검사
    @POST("users/email/mail")
    @Headers("Auth: false")
    suspend fun postCheckEmailCode(
        @Body postCheckEmailCodeBody: PostCheckEmailCodeBody
    ): NetworkState<Void>

    // 임시 비밀번호 발송
    @GET("users/password/find")
    @Headers("Auth: false")
    suspend fun getSendTempPassword(
        @Query("email") email: String
    ): NetworkState<Void>

    // 로그인
    @POST("users/login")
    @Headers("Auth: false")
    suspend fun postLogin(
        @Body postLoginBody: PostLoginBody
    ): NetworkState<PostLoginEntity>

}