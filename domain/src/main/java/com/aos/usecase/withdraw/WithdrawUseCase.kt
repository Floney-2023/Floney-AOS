package com.aos.usecase.withdraw

import com.aos.model.user.DeleteWithdrawModel
import com.aos.model.user.PostSignUpUserModel
import com.aos.repository.UserRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        accessToken: String,
        type: String,
        reason: String?
    ): Result<DeleteWithdrawModel> {
        return userRepository.deleteWithdraw(accessToken, type, reason)
    }

}