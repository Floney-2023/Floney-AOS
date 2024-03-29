package com.aos.usecase

import com.aos.model.PostSignUpUserModel
import com.aos.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean
    ): Result<PostSignUpUserModel> {
        return userRepository.postSignUpUser(email, nickname, password, receiveMarketing)
    }

}