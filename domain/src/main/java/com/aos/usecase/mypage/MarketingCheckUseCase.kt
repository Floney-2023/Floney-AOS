package com.aos.usecase.mypage

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.user.GetReceiveMarketingModel
import com.aos.repository.BookRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class MarketingCheckUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    suspend operator fun invoke(
    ): Result<GetReceiveMarketingModel> {
        return userRepository.getMarketingCheck()
    }

}