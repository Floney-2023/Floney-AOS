package com.aos.usecase.password

import com.aos.repository.UserRepository
import javax.inject.Inject

class SendTempPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String
    ): Result<Void?> {
        return userRepository.getSendTempPassword(email)
    }

}