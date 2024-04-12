package com.aos.usecase.settlement

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksOutComesUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        usersEmails : List<String>,
        startDate : String,
        endDate : String,
        bookKey : String
    ): Result<UiOutcomesSelectModel> {
        return bookRepository.postBooksOutcomes(usersEmails, startDate, endDate, bookKey)
    }

}