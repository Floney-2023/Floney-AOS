package com.aos.usecase.booksetting

import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.UiBookSettingModel
import com.aos.repository.BookRepository
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject

class BooksExcelUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend operator fun invoke(
        bookKey : String,
        excelDuration: String,
        currentDate : String
    ): Result<ResponseBody> {
        return bookRepository.postBooksExcel(bookKey, excelDuration, currentDate)
    }

}