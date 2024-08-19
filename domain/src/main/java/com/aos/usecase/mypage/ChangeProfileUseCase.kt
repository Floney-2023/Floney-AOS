package com.aos.usecase.mypage

import com.aos.repository.UserRepository
import javax.inject.Inject

class ChangeProfileUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        profileImg: String
    ): Result<Void?> {
        return userRepository.getChangeProfile(profileImg)
    }

}