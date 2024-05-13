package com.aos.usecase.settlement

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.NaverShortenUrlModel
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class NaverShortenUrlUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        id: String,
        secretKey: String,
        url : String
    ): Result<NaverShortenUrlModel> {
        return bookRepository.postShortenUrl(id, secretKey, url)
    }

}