package com.aos.data.repository.remote.book

import com.aos.data.api.BookService
import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
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

    override suspend fun getBooksDays(
        bookKey: String,
        date: String,
    ): NetworkState<GetBookDaysEntity> {
        return bookService.getBookDays(bookKey, date)
    }

    override suspend fun getBookInfo(bookKey: String): NetworkState<GetBookInfoEntity> {
        return bookService.getBookInfo(bookKey)
    }
    override suspend fun postBooksJoin(
        postBooksJoinBody : PostBooksJoinBody
    ): NetworkState<PostBooksJoinEntity> {
        return bookService.postBooksJoin(postBooksJoinBody)
    }
    override suspend fun postBooksCreate(
        postBooksCreateBody : PostBooksCreateBody
    ): NetworkState<PostBooksCreateEntity> {
        return bookService.postBooksCreate(postBooksCreateBody)
    }

}