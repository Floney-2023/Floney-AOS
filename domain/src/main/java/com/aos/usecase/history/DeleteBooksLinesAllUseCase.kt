package com.aos.usecase.history

import com.aos.repository.BookRepository
import javax.inject.Inject

class DeleteBooksLinesAllUseCase @Inject constructor(private val bookRepository: BookRepository ) {

    suspend operator fun invoke(
        bookLineKey: Int
    ): Result<Void?>  {
        return bookRepository.deleteBooksLinesAll(bookLineKey)
    }

}