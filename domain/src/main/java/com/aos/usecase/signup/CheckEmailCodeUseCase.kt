package com.aos.usecase

import com.aos.repository.UserRepository
import javax.inject.Inject

class CheckEmailCodeUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        email: String,
        code: String
    ): Result<Void?> {
        return userRepository.postCheckEmailCode(email, code)
    }

}