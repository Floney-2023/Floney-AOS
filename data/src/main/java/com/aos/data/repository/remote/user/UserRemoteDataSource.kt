package com.aos.data.repository.remote.user

import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PostLoginBody
import com.aos.data.entity.request.user.PutPasswordChangeBody
import com.aos.data.entity.response.home.GetReceiveMarketingEntity
import com.aos.data.entity.response.user.GetMypageSearchEntity
import com.aos.data.entity.response.user.PostLoginEntity
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
        postCheckEmailCodeBody: PostCheckEmailCodeBody
    ): NetworkState<Void>

    // 이메일 코드 확인
    suspend fun getSendEmailPassword(
        email: String
    ): NetworkState<Void>

    // 로그인
    suspend fun postLogin(
        email: String,
        password: String
    ): NetworkState<PostLoginEntity>

    // 비밀번호 변경
    suspend fun putPasswordChange(
        putPasswordChangeBody: PutPasswordChangeBody
    ): NetworkState<Void>

    // 닉네임 변경
    suspend fun getNicknameChange(
        nickname: String
    ): NetworkState<Void>

    // 유저 마케팅 수신 동의 여부 변경
    suspend fun putMarketingChange(
        agree: Boolean
    ): NetworkState<Void>

    // 유저 마케팅 수신 동의 여부 확인
    suspend fun getMarketingCheck(
    ): NetworkState<GetReceiveMarketingEntity>

    // 마이페이지 조회
    suspend fun getMypageSearch(
    ): NetworkState<GetMypageSearchEntity>

    // 로그아웃
    suspend fun getLogout(
        accessToken: String
    ): NetworkState<Void>
}