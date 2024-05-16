package com.aos.usecase.history

import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookFavorite
import com.aos.repository.BookRepository
import javax.inject.Inject

class GetBookFavoriteUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(bookKey: String, categoryType: String): Result<List<UiBookFavorite>> {
        return bookRepository.getBookFavorite(bookKey, categoryType)
    }

}