package com.aos.repository

import com.aos.model.PostSignUpUserModel

interface UserRepository {

    // 회원가입
    suspend fun postSignUpUser(email: String, nickname: String, password: String, receiveMarketing: Boolean): Result<PostSignUpUserModel>
    suspend fun getSendEmail(email: String): Result<Void?>

}