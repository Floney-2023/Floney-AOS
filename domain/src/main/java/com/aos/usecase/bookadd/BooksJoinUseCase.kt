package com.aos.usecase.bookadd

import com.aos.model.book.PostBooksJoinModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksJoinUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        code : String
    ): Result<PostBooksJoinModel> {
        return bookRepository.postBooksJoin(code)
    }

}