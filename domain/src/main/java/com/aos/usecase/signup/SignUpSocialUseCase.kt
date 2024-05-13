package com.aos.usecase.signup

import com.aos.model.user.PostSignUpUserModel
import com.aos.repository.UserRepository
import javax.inject.Inject

class SignUpSocialUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        provider: String,
        token: String,
        email: String,
        nickname: String,
        receiveMarketing: Boolean
    ): Result<PostSignUpUserModel> {
        return userRepository.postSocialSignUpUser(provider, token, email, nickname, receiveMarketing)
    }

}