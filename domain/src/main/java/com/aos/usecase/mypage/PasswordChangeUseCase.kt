package com.aos.usecase.mypage

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.repository.BookRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class PasswordChangeUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
        newPassword : String,
        oldPassword : String
    ): Result<Void?> {
        return userRepository.putPasswordChange(newPassword, oldPassword)
    }

}