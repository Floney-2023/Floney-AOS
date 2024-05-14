package com.aos.usecase.login

import com.aos.model.user.PostLoginModel
import com.aos.repository.UserRepository
import com.aos.util.NetworkState
import javax.inject.Inject

class SocialLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        provider: String,
        token: String
    ): Result<PostLoginModel> {
        return userRepository.getSocialLogin(provider, token)
    }
}