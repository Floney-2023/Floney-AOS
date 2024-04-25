package com.aos.usecase.booksetting

import com.aos.model.book.GetBooksInfoCurrencyModel
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksInfoCurrencyModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksCurrencySearchUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String
    ): Result<GetBooksInfoCurrencyModel> {
        return bookRepository.getBooksInfoCurrency(bookKey)
    }

}