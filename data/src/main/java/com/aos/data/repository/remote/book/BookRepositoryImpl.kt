package com.aos.data.repository.remote.book

import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.mapper.toGetCheckUserBookModel
import com.aos.data.mapper.toGetsettleUpLastModel
import com.aos.data.mapper.toUiBookInfoModel
import com.aos.data.mapper.toUiBookMonthModel
import com.aos.data.mapper.toPostBooksCreateModel
import com.aos.data.mapper.toPostBooksJoinModel
import com.aos.data.mapper.toUiMemberSelectModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.UiMemberSelectModel
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

    override suspend fun postBooksJoin(code : String): Result<PostBooksJoinModel> {
        when (val data =
            bookDataSource.postBooksJoin(PostBooksJoinBody(code))) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksJoinModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun postBooksCreate(name : String, profileImg : String): Result<PostBooksCreateModel> {
        when (val data =
            bookDataSource.postBooksCreate(PostBooksCreateBody(name, profileImg))) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksCreateModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun getSettlementLast(bookKey: String): Result<GetSettlementLastModel> {
        when (val data =
            bookDataSource.getSettlementLast(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toGetsettleUpLastModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }
    override suspend fun getBooksUsers(bookKey: String): Result<UiMemberSelectModel> {
        when (val data =
            bookDataSource.getBooksUsers(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toUiMemberSelectModel())
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