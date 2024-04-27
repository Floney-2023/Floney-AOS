package com.aos.usecase.settlement

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class SettlementLastUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        bookKey : String
    ): Result<GetSettlementLastModel> {
        return bookRepository.getSettlementLast(bookKey)
    }

}