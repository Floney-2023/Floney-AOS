package com.aos.data.repository.remote.book

import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.util.NetworkState

interface BookRemoteDataSource {

    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>
    suspend fun postBooksJoin(postBooksJoinBody : PostBooksJoinBody): NetworkState<PostBooksJoinEntity>
    suspend fun postBooksCreate(postBooksCreateBody : PostBooksCreateBody): NetworkState<PostBooksCreateEntity>

}