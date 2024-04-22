package com.aos.usecase.booksetting

import com.aos.model.book.PostBooksCategoryAddModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksCategoryAddUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String,
        parent : String,
        name : String
    ): Result<PostBooksCategoryAddModel> {
        return bookRepository.postBooksCategoryAdd(bookKey, parent, name)
    }

}