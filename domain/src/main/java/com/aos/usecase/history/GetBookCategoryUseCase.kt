package com.aos.usecase.history

import com.aos.model.book.UiBookCategory
import com.aos.repository.BookRepository

class GetBookCategoryUseCase(private val bookRepository: BookRepository) {

    suspend operator fun invoke(bookKey: String, parent: String): Result<List<UiBookCategory>> {
        return bookRepository.getBookCategory(bookKey, parent)
    }

}