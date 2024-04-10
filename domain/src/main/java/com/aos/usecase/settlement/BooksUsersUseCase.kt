package com.aos.usecase.settlement

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksUsersUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        bookKey : String
    ): Result<UiMemberSelectModel> {
        return bookRepository.getBooksUsers(bookKey)
    }

}