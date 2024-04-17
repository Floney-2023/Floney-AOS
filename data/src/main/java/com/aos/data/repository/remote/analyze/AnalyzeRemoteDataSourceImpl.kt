package com.aos.data.repository.remote.analyze

import com.aos.data.api.AnalyzeService
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryEntity
import com.aos.util.NetworkState
import javax.inject.Inject

class AnalyzeRemoteDataSourceImpl @Inject constructor(private val analyzeService: AnalyzeService) :
    AnalyzeRemoteDataSource {
    override suspend fun postAnalyzeCategory(
        body: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryEntity> {
        return analyzeService.postAnalyzeCategory(body)
    }
}