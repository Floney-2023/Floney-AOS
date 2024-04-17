package com.aos.data.api

import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryEntity
import com.aos.util.NetworkState
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalyzeService {

    @POST("analyze/category")
    suspend fun postAnalyzeCategory(
        @Body postAnalyzeCategoryBody: PostAnalyzeCategoryBody
    ): NetworkState<PostAnalyzeCategoryEntity>

}