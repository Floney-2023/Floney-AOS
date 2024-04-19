package com.aos.usecase.booksetting

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksInfoCurrencyModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksCurrencyChangeUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        currency : String,
        bookKey : String
    ): Result<PostBooksInfoCurrencyModel> {
        return bookRepository.postBooksInfoCurrency(currency, bookKey)
    }

}