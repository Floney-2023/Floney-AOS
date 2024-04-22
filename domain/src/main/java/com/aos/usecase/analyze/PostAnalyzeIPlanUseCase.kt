package com.aos.usecase.analyze

import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel
import com.aos.repository.AnalyzeRepository
import javax.inject.Inject

class PostAnalyzeIPlanUseCase @Inject constructor(
    private val analyzeRepository: AnalyzeRepository
){
    suspend operator fun invoke(
        bookKey: String,
        date: String
    ): Result<UiAnalyzePlanModel> {
        return analyzeRepository.postAnalyzeBudget(bookKey, date)
    }
}