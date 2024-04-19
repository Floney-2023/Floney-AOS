package com.aos.usecase.analyze

import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.repository.AnalyzeRepository
import javax.inject.Inject

class PostAnalyzeInComeCategoryUseCase @Inject constructor(
    private val analyzeRepository: AnalyzeRepository
){
    suspend operator fun invoke(
        bookKey: String,
        root: String,
        date: String
    ): Result<UiAnalyzeCategoryInComeModel> {
        return analyzeRepository.postAnalyzeInComeCategory(bookKey, root, date)
    }
}