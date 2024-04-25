package com.aos.usecase.logout

import com.aos.model.user.PostLoginModel
import com.aos.repository.UserRepository
import com.aos.util.NetworkState
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        accessToken: String
    ): Result<Void?> {
        return userRepository.getLogout(accessToken)
    }
}