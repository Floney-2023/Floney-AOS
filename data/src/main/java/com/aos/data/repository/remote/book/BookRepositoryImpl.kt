package com.aos.data.repository.remote.book

import com.aos.data.mapper.toGetCheckUserBookModel
import com.aos.data.mapper.toUiBookInfoModel
import com.aos.data.mapper.toUiBookMonthModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.home.UiBookMonthModel
import com.aos.repository.BookRepository
import com.aos.util.NetworkState
import timber.log.Timber
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDataSource: BookRemoteDataSource) :
    BookRepository {

    override suspend fun getCheckUserBook(): Result<GetCheckUserBookModel> {
        when (val data =
            bookDataSource.getCheckUserBook()) {
            is NetworkState.Success -> return Result.success(data.body.toGetCheckUserBookModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun getBooksMonth(bookKey: String, date: String): Result<UiBookMonthModel> {
        when (val data =
            bookDataSource.getBooksMonth(bookKey, date)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookMonthModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError ->{
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun getBooksDays(bookKey: String, date: String): Result<UiBookDayModel> {
        when (val data =
            bookDataSource.getBooksDays(bookKey, date)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookMonthModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError ->{
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun getBookInfo(bookKey: String): Result<UiBookInfoModel> {
        when (val data =
            bookDataSource.getBookInfo(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookInfoModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError ->{
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }
}