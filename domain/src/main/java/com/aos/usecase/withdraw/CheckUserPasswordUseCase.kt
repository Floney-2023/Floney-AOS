package com.aos.usecase.withdraw

import com.aos.model.user.DeleteWithdrawModel
import com.aos.model.user.PostSignUpUserModel
import com.aos.repository.UserRepository
import javax.inject.Inject

class CheckUserPasswordUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        password : String
    ): Result<Void?> {
        return userRepository.postCheckPassword(password)
    }

}