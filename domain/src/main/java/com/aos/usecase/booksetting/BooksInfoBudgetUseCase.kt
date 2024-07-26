package com.aos.usecase.booksetting

import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksInfoBudgetUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey: String,
        budget: Long,
        date: String
    ): Result<Void?> {
        return bookRepository.postBooksInfoBudget(bookKey, budget, date)
    }

}