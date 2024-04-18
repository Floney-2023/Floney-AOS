package com.aos.usecase.booksetting

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksNameChangeUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        name : String,
        bookKey : String
    ): Result<Void?> {
        return bookRepository.postBooksName(name, bookKey)
    }

}