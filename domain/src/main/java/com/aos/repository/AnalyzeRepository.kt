package com.aos.repository

import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel

interface AnalyzeRepository {

    // 분석 - 지출 / 수입 가져오기
    suspend fun postAnalyzeOutComeCategory(bookKey: String, root: String, date: String): Result<UiAnalyzeCategoryOutComeModel>

    // 분석 - 지출 / 수입 가져오기
    suspend fun postAnalyzeInComeCategory(bookKey: String, root: String, date: String): Result<UiAnalyzeCategoryInComeModel>

}