package com.aos.data.repository.remote

import com.aos.data.entity.request.user.PostCheckEmailCode
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.util.NetworkState

interface UserRemoteDataSource {

    // 회원가입
    suspend fun postSignUpUser(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity>

    // 이메일 전송
    suspend fun getSendEmail(
        email: String
    ): NetworkState<Void>


    // 이메일 코드 확인
    suspend fun postCheckEmailCode(
        postCheckEmailCode: PostCheckEmailCode
    ): NetworkState<Void>

}