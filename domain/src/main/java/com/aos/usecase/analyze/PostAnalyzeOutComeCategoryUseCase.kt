package com.aos.usecase.analyze

import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.repository.AnalyzeRepository
import javax.inject.Inject

class PostAnalyzeOutComeCategoryUseCase @Inject constructor(
    private val analyzeRepository: AnalyzeRepository
){
    suspend operator fun invoke(
        bookKey: String,
        root: String,
        date: String
    ): Result<UiAnalyzeCategoryOutComeModel> {
        return analyzeRepository.postAnalyzeOutComeCategory(bookKey, root, date)
    }
}