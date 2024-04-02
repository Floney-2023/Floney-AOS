package com.aos.data.repository.remote.book

import com.aos.data.api.BookService
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState
import javax.inject.Inject

class BookRemoteDataSourceImpl @Inject constructor(private val bookService: BookService): BookRemoteDataSource {

    override suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity> {
        return bookService.getCheckUserBook()
    }

    override suspend fun getBooksMonth(bookKey: String, date: String): NetworkState<GetBookMonthEntity> {
        return bookService.getBookMonth(bookKey, date)
    }
}