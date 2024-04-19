package com.aos.data.repository.remote.analyze

import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState

interface AnalyzeRemoteDataSource {

    suspend fun postAnalyzeOutComeCategory(body: PostAnalyzeCategoryBody): NetworkState<PostAnalyzeCategoryOutComeEntity>
    suspend fun postAnalyzeInComeCategory(body: PostAnalyzeCategoryBody): NetworkState<PostAnalyzeCategoryInComeEntity>
}