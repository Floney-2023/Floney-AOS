package com.aos.usecase.history

import com.aos.repository.BookRepository
import javax.inject.Inject

class DeleteBookLineUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(
        bookLineKey: String
    ):Result<Void?> {
        return bookRepository.deleteBookLines(bookLineKey)
    }
}