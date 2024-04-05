package com.aos.data.repository.remote.book

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

}