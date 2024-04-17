package com.aos.repository

import com.aos.model.analyze.UiAnalyzeCategoryModel

interface AnalyzeRepository {

    // 분석 - 지출 / 수입 가져오기
    suspend fun postAnalyzeCategory(bookKey: String, root: String, date: String): Result<UiAnalyzeCategoryModel>

}