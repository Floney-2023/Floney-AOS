package com.aos.usecase.history

import com.aos.model.book.UiBookFavoriteModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class GetBookFavoriteUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(bookKey: String, categoryType: String): Result<List<UiBookFavoriteModel>> {
        return bookRepository.getBookFavorite(bookKey, categoryType)
    }

}