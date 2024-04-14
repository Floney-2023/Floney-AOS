package com.aos.data.repository.remote.book

import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.request.book.PostBooksLinesBody
import com.aos.data.entity.request.book.PostBooksLinesEntity
import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState

interface BookRemoteDataSource {

    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>
    suspend fun getBooksMonth(bookKey: String, date: String): NetworkState<GetBookMonthEntity>
    suspend fun getBooksDays(bookKey: String, date: String): NetworkState<GetBookDaysEntity>
    suspend fun getBookInfo(bookKey: String): NetworkState<GetBookInfoEntity>
    suspend fun postBooksJoin(postBooksJoinBody : PostBooksJoinBody): NetworkState<PostBooksJoinEntity>
    suspend fun postBooksCreate(postBooksCreateBody : PostBooksCreateBody): NetworkState<PostBooksCreateEntity>
    suspend fun getBookCategory(bookKey: String, parent: String): NetworkState<List<GetBookCategoryEntity>>
    suspend fun postBooksLines(moneyData: PostBooksLinesBody): NetworkState<PostBooksLinesEntity>

}