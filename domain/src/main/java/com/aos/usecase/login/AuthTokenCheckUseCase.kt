package com.aos.usecase.login

import com.aos.repository.UserRepository
import javax.inject.Inject

class AuthTokenCheckUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        provider: String,
        token: String
    ): Result<Boolean>{
        return userRepository.getAuthTokenCheck(provider, token)
    }

}