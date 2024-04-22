package com.aos.data.repository.remote.analyze

import com.aos.data.entity.request.analyze.PostAnalyzeBudgetBody
import com.aos.data.entity.request.analyze.PostAnalyzeCategoryBody
import com.aos.data.entity.request.book.PostBooksChangeBody
import com.aos.data.mapper.toPostBooksLinesChangeModel
import com.aos.data.mapper.toUiAnalyzeModel
import com.aos.data.mapper.toUiAnalyzePlanModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.analyze.UiAnalyzeCategoryInComeModel
import com.aos.model.analyze.UiAnalyzeCategoryOutComeModel
import com.aos.model.analyze.UiAnalyzePlanModel
import com.aos.repository.AnalyzeRepository
import com.aos.util.NetworkState
import timber.log.Timber
import javax.inject.Inject

class AnalyzeRepositoryImpl @Inject constructor(private val analyzeDataSourceImpl: AnalyzeRemoteDataSourceImpl) :
    AnalyzeRepository {

    override suspend fun postAnalyzeOutComeCategory(
        bookKey: String, root: String, date: String
    ): Result<UiAnalyzeCategoryOutComeModel> {
        when (val data = analyzeDataSourceImpl.postAnalyzeOutComeCategory(
            PostAnalyzeCategoryBody(
                bookKey, root, date
            )
        )) {
            is NetworkState.Success -> return Result.success(data.body.toUiAnalyzeModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e(data.t?.message)
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun postAnalyzeInComeCategory(
        bookKey: String,
        root: String,
        date: String
    ): Result<UiAnalyzeCategoryInComeModel> {
        when (val data = analyzeDataSourceImpl.postAnalyzeInComeCategory(
            PostAnalyzeCategoryBody(
                bookKey, root, date
            )
        )) {
            is NetworkState.Success -> return Result.success(data.body.toUiAnalyzeModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e(data.t?.message)
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun postAnalyzeBudget(
        bookKey: String,
        date: String
    ): Result<UiAnalyzePlanModel> {
        when (val data = analyzeDataSourceImpl.postAnalyzeBudget(
            PostAnalyzeBudgetBody(
                bookKey, date
            )
        )) {
            is NetworkState.Success -> return Result.success(data.body.toUiAnalyzePlanModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e(data.t?.message)
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }
}