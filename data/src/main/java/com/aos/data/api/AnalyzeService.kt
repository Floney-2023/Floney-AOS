package com.aos.data.api

import com.aos.data.entity.request.analyze.PostAnalyzeBudgetBody
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeBudgetEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryInComeEntity
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryOutComeEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalyzeService {

    // 분석 - 지출
    @POST("analyze/category")
    suspend fun postAnalyzeOutComeCategory(
        @Body postAnalyzeCategoryBody: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryOutComeEntity>

    // 분석 - 수입
    @POST("analyze/category")
    suspend fun postAnalyzeInComeCategory(
        @Body postAnalyzeCategoryBody: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryInComeEntity>


    // 분석 - 예산
    @POST("analyze/budget")
    suspend fun postAnalyzeBudget(
        @Body postAnalyzeBudgetBody: PostAnalyzeBudgetBody
    ): NetworkState<PostAnalyzeBudgetEntity>

}