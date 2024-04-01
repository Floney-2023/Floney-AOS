package com.aos.repository

import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel

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

}