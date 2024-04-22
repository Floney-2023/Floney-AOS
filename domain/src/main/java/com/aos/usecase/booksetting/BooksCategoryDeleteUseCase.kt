package com.aos.usecase.booksetting

import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksCategoryDeleteUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String,
        parent : String,
        name : String
    ): Result<Void?> {
        return bookRepository.deleteBookCategory(bookKey, parent, name)
    }

}