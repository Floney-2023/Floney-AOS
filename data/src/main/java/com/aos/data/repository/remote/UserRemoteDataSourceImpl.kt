package com.aos.data.repository.remote

import com.aos.data.api.UserService
import com.aos.data.entity.request.user.PostCheckEmailCode
import com.aos.data.entity.request.user.PostSignUpUser
import com.aos.data.entity.response.user.PostSignUpUserEntity
import com.aos.util.NetworkState
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userService: UserService) :
    UserRemoteDataSource {

    // 회가입
    override suspend fun postSignUpUser(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean,
    ): NetworkState<PostSignUpUserEntity> {
        return userService.postSignUpUser(
            PostSignUpUser(
                email = email,
                nickname = nickname,
                password = password,
                receiveMarketing = receiveMarketing
            )
        )
    }

    override suspend fun getSendEmail(email: String): NetworkState<Void> {
        return userService.getSendEmail(email)
    }

    override suspend fun postCheckEmailCode(postCheckEmailCode: PostCheckEmailCode): NetworkState<Void> {
        return userService.postCheckEmailCode(postCheckEmailCode)
    }
}