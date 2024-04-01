package com.aos.usecase.signup

import com.aos.repository.UserRepository
import javax.inject.Inject

class SendEmailUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        email: String
    ): Result<Void?> {
        return userRepository.getSendEmail(email)
    }

}