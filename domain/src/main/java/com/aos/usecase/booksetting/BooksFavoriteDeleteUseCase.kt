package com.aos.usecase.booksetting

import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksFavoriteDeleteUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String,
        favoriteId: Int
    ): Result<Void?> {
        return bookRepository.deleteBookFavorite(bookKey, favoriteId)
    }

}