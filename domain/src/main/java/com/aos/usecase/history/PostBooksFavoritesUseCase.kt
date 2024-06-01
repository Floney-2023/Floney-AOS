package com.aos.usecase.history

import com.aos.model.book.PostBookFavoriteModel
import com.aos.model.book.PostBooksLinesModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class PostBooksFavoritesUseCase @Inject constructor(private val bookRepository: BookRepository) {
    suspend operator fun invoke(
       bookKey: String,
       money: Double,
       description: String,
       lineCategoryName: String,
       lineSubcategoryName: String,
       assetSubcategoryName: String,
       exceptStatus : Boolean
    ): Result<PostBookFavoriteModel> {
        return bookRepository.postBooksFavorites(bookKey, money, description, lineCategoryName, lineSubcategoryName, assetSubcategoryName,exceptStatus)
    }
}