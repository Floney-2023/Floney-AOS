package com.aos.usecase.login

import com.aos.model.user.PostLoginModel
import com.aos.repository.UserRepository
import com.aos.util.NetworkState
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<PostLoginModel> {
        return userRepository.postLogin(email, password)
    }
}