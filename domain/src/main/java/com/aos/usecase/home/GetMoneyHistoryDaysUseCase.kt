package com.aos.usecase.home

import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookMonthModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class GetMoneyHistoryDaysUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(
        bookKey: String,
        date: String
    ): Result<UiBookDayModel> {
        return bookRepository.getBooksDays(bookKey, date)
    }

}