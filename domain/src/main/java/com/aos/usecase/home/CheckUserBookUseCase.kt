package com.aos.usecase.home

import com.aos.model.home.GetCheckUserBookModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class CheckUserBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(): Result<GetCheckUserBookModel> {
        return bookRepository.getCheckUserBook()
    }

}