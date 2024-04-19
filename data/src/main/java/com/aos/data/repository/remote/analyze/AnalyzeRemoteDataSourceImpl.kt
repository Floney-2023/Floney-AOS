package com.aos.data.repository.remote.analyze

import com.aos.data.api.AnalyzeService
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState
import javax.inject.Inject

class AnalyzeRemoteDataSourceImpl @Inject constructor(private val analyzeService: AnalyzeService) :
    AnalyzeRemoteDataSource {
    override suspend fun postAnalyzeOutComeCategory(
        body: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryOutComeEntity> {
        return analyzeService.postAnalyzeOutComeCategory(body)
    }

    override suspend fun postAnalyzeInComeCategory(body: PostAnalyzeCategoryBody): NetworkState<PostAnalyzeCategoryInComeEntity> {
        return analyzeService.postAnalyzeInComeCategory(body)
    }
}