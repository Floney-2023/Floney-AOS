package com.aos.usecase.analyze

import com.aos.model.analyze.UiAnalyzeCategoryModel
import com.aos.repository.AnalyzeRepository
import javax.inject.Inject

class PostAnalyzeCategoryUseCase @Inject constructor(
    private val analyzeRepository: AnalyzeRepository
){
    suspend operator fun invoke(
        bookKey: String,
        root: String,
        date: String
    ): Result<UiAnalyzeCategoryModel> {
        return analyzeRepository.postAnalyzeCategory(bookKey, root, date)
    }
}