package com.aos.usecase.bookadd

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class BooksCreateUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        name : String,
        profileImg : String
    ): Result<PostBooksCreateModel> {
        return bookRepository.postBooksCreate(name, profileImg)
    }

}