package com.aos.usecase.mypage

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.repository.BookRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class MarketingChangeUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
        agree : Boolean
    ): Result<Void?> {
        return userRepository.putMarketingChange(agree)
    }

}