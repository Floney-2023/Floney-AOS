package com.aos.usecase.home

import com.aos.model.home.UiBookMonthModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class SearchBookMonthUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(
        bookKey: String,
        date: String
    ): Result<UiBookMonthModel> {
        return bookRepository.getBooksMonth(bookKey, date)
    }

}