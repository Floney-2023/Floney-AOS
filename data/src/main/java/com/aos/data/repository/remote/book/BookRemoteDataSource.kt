package com.aos.data.repository.remote.book

import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState

interface BookRemoteDataSource {

    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>
    suspend fun getBooksMonth(bookKey: String, date: String): NetworkState<GetBookMonthEntity>

}