package com.aos.usecase.bookadd

import com.aos.repository.BookRepository
import com.aos.repository.UserRepository
import javax.inject.Inject

class ChangeBookImgUseCase @Inject constructor(private val bookRepository: BookRepository) {

    suspend operator fun invoke(
        bookKey: String,
        profileImg: String
    ): Result<Void?> {
        return bookRepository.postChangeBookImg(bookKey, profileImg)
    }

}