package com.aos.usecase.bookadd

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookEntranceModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksEntranceUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        code : String
    ): Result<UiBookEntranceModel> {
        return bookRepository.getBooks(code)
    }

}