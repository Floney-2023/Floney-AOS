package com.aos.usecase.booksetting

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksRepeatDeleteUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        repeatLineId : Int
    ): Result<Void?> {
        return bookRepository.deleteBooksRepeat(repeatLineId)
    }

}