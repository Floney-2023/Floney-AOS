package com.aos.repository

import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel

interface AnalyzeRepository {

    // 분석 - 지출 가져오기
    suspend fun postAnalyzeOutComeCategory(bookKey: String, root: String, date: String): Result<UiAnalyzeCategoryOutComeModel>

    // 분석 - 수입 가져오기
    suspend fun postAnalyzeInComeCategory(bookKey: String, root: String, date: String): Result<UiAnalyzeCategoryInComeModel>

    // 분석 - 예산
    suspend fun postAnalyzeBudget(bookKey: String, date: String): Result<UiAnalyzePlanModel>

}