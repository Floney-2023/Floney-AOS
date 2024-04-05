package com.aos.repository

import com.aos.model.user.GetReceiveMarketingModel
import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel
import com.aos.model.user.UiMypageSearchModel

interface UserRepository {

    // 회원가입
    suspend fun postSignUpUser(email: String, nickname: String, password: String, receiveMarketing: Boolean): Result<PostSignUpUserModel>
    // 회원가입 이메일 전송
    suspend fun getSendEmail(email: String): Result<Void?>
    // 이메일 인증
    suspend fun postCheckEmailCode(email: String, code: String): Result<Void?>
    // 임시 비밀번호 발송
    suspend fun getSendTempPassword(email: String): Result<Void?>
    // 임시 비밀번호 발송
    suspend fun postLogin(email: String, password: String): Result<PostLoginModel>
    // 비밀번호 변경
    suspend fun putPasswordChange(newPassword: String, oldPassword: String): Result<Void?>
    // 유저 닉네임 변경
    suspend fun getNicknameChange(nickname: String): Result<Void?>
    // 유저 마케팅 수신 동의 여부 변경
    suspend fun putMarketingChange(agree: Boolean): Result<Void?>
    // 유저 마케팅 수신 동의 여부 조회
    suspend fun getMarketingCheck(): Result<GetReceiveMarketingModel>
    // 마이페이지 조회
    // 유저 마케팅 수신 동의 여부 조회
    suspend fun getMypageSearch(): Result<UiMypageSearchModel>
}