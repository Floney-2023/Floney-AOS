package com.aos.data.mapper

import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.response.analyze.PostAnalyzeCategoryEntity
import com.aos.model.analyze.AnalyzeResult
import com.aos.model.analyze.UiAnalyzeCategoryModel
import java.text.NumberFormat

fun PostAnalyzeCategoryEntity.toUiAnalyzeModel(): UiAnalyzeCategoryModel {
    return UiAnalyzeCategoryModel(
        "${NumberFormat.getNumberInstance().format(this.total)}원",
        "${NumberFormat.getNumberInstance().format(this.differance)}원",
        this.analyzeResult.map {
            AnalyzeResult(it.category, it.money)
        }
    )
}