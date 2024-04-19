package com.aos.data.api

import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalyzeService {

    @POST("analyze/category")
    suspend fun postAnalyzeOutComeCategory(
        @Body postAnalyzeCategoryBody: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryOutComeEntity>

    @POST("analyze/category")
    suspend fun postAnalyzeInComeCategory(
        @Body postAnalyzeCategoryBody: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryInComeEntity>

}