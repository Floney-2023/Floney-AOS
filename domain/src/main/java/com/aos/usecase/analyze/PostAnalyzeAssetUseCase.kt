package com.aos.usecase.analyze

import com.aos.model.analyze.UiAnalyzeAssetModel
import com.aos.repository.AnalyzeRepository
import javax.inject.Inject

class PostAnalyzeAssetUseCase @Inject constructor(private val analyzeRepository: AnalyzeRepository) {

    suspend operator fun invoke(
        bookKey: String,
        date: String
    ): Result<UiAnalyzeAssetModel> {
        return analyzeRepository.postAnalyzeAsset(bookKey, date)
    }

}