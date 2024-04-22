package com.aos.usecase.booksetting

import com.aos.model.book.GetBooksInfoCurrencyModel
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksInfoCurrencyModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookBudgetModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksBudgetCheckUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String,
        budget : String
    ): Result<UiBookBudgetModel> {
        return bookRepository.getBooksBudget(bookKey, budget)
    }

}