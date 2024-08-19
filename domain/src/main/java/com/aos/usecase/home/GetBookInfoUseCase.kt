package com.aos.usecase.home

import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookInfoModel
import com.aos.repository.BookRepository
import javax.inject.Inject

class GetBookInfoUseCase @Inject constructor(
    private val bookRepository: BookRepository
){

    suspend operator fun invoke(
        code: String
    ): Result<UiBookInfoModel> {
        return bookRepository.getBookInfo(code)
    }

}