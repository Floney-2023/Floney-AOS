package com.aos.usecase.booksetting

import com.aos.model.book.UiBookRepeatModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksRepeatGetUseCase @Inject constructor(
    private val bookRepository: BookRepository) {

    suspend operator fun invoke(bookKey: String, categoryType: String): Result<List<UiBookRepeatModel>> {
        return bookRepository.getBooksRepeat(bookKey, categoryType)
    }

}