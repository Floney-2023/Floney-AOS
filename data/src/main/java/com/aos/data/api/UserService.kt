package com.aos.data.api

import com.aos.data.entity.request.user.PostCheckEmailCode
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    // 회원가입
    @POST
    suspend fun postSignUpUser(
        @Field("email") email: String,
        @Field("nickname") nickname: String,
        @Field("password") password: String,
        @Field("receiveMarketing") receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity>

    // 이메일 전송
    @GET("users/email/mail")
    suspend fun getSendEmail(
        @Query("email") email: String
    ): NetworkState<Void>


    // 이메일 코드 검사
    @POST("users/email/mail")
    suspend fun postCheckEmailCode(
        @Body postCheckEmailCode: PostCheckEmailCode
    ): NetworkState<Void>

}