package com.aos.data.repository.remote.analyze

import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryEntity
import com.aos.util.NetworkState

interface AnalyzeRemoteDataSource {

    suspend fun postAnalyzeCategory(body: PostAnalyzeCategoryBody): NetworkState<PostAnalyzeCategoryEntity>
}