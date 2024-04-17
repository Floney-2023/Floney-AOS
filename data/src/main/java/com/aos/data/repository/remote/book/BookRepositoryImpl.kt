package com.aos.data.repository.remote.book

import com.aos.data.entity.request.book.PostBooksChangeBody
import com.aos.data.entity.request.book.Duration
import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.request.book.PostBooksLinesBody
import com.aos.data.entity.request.book.PostBooksOutcomesBody
import com.aos.data.entity.request.book.PostSettlementAddBody
import com.aos.data.mapper.toGetCheckUserBookModel
import com.aos.data.mapper.toGetsettleUpLastModel
import com.aos.data.mapper.toUiBookInfoModel
import com.aos.data.mapper.toUiBookMonthModel
import com.aos.data.mapper.toPostBooksCreateModel
import com.aos.data.mapper.toPostBooksJoinModel
import com.aos.data.mapper.toPostBooksLinesChangeModel
import com.aos.data.mapper.toPostBooksLinesModel
import com.aos.data.mapper.toUiBookCategory
import com.aos.data.mapper.toPostSettlementAddModel
import com.aos.data.mapper.toUiBookSettingModel
import com.aos.data.mapper.toUiMemberSelectModel
import com.aos.data.mapper.toUiOutcomesSelectModel
import com.aos.data.mapper.toUiSettlementSeeModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.book.PostBooksChangeModel
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.PostBooksLinesModel
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookSettingModel
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.UiSettlementSeeModel
import com.aos.model.settlement.settleOutcomes
import com.aos.repository.BookRepository
import com.aos.util.NetworkState
import timber.log.Timber
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val bookDataSource: BookRemoteDataSource) :
    BookRepository {

    override suspend fun getCheckUserBook(): Result<GetCheckUserBookModel> {
        when (val data = bookDataSource.getCheckUserBook()) {
            is NetworkState.Success -> return Result.success(data.body.toGetCheckUserBookModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun getBooksMonth(bookKey: String, date: String): Result<UiBookMonthModel> {
        when (val data = bookDataSource.getBooksMonth(bookKey, date)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookMonthModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun getBooksDays(bookKey: String, date: String): Result<UiBookDayModel> {
        when (val data = bookDataSource.getBooksDays(bookKey, date)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookMonthModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun getBookInfo(bookKey: String): Result<UiBookInfoModel> {
        when (val data = bookDataSource.getBookInfo(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookInfoModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                Timber.e("UnknownError ${data.errorState}, ${data.t}")
                return Result.failure(IllegalStateException("unKnownError"))
            }
        }
    }

    override suspend fun postBooksJoin(code: String): Result<PostBooksJoinModel> {
        when (val data = bookDataSource.postBooksJoin(PostBooksJoinBody(code))) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksJoinModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun postBooksCreate(
        name: String, profileImg: String
    ): Result<PostBooksCreateModel> {
        when (val data = bookDataSource.postBooksCreate(PostBooksCreateBody(name, profileImg))) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksCreateModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun getBookCategory(
        bookKey: String,
        parent: String,
    ): Result<List<UiBookCategory>> {
        when (val data = bookDataSource.getBookCategory(bookKey, parent)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookCategory())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun postBooksLines(
        bookKey: String,
        money: Int,
        flow: String,
        asset: String,
        line: String,
        lineDate: String,
        description: String,
        except: Boolean,
        nickname: String,
        repeatDuration: String
    ): Result<PostBooksLinesModel> {
        when (val data = bookDataSource.postBooksLines(
            PostBooksLinesBody(
                bookKey,
                money,
                flow,
                asset,
                line,
                lineDate,
                description,
                except,
                nickname,
                repeatDuration
            )
        )) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksLinesModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )

            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun postBooksLinesChange(
        lineId: Int,
        bookKey: String,
        money: Int,
        flow: String,
        asset: String,
        line: String,
        lineDate: String,
        description: String,
        except: Boolean,
        nickname: String
    ): Result<PostBooksChangeModel> {
        when (val data = bookDataSource.postBooksLinesChange(
            PostBooksChangeBody(
                lineId, bookKey, money, flow, asset, line, lineDate, description, except, nickname
            )
        )) {
            is NetworkState.Success -> return Result.success(data.body.toPostBooksLinesChangeModel())
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
    override suspend fun postBooksOutcomes(userEmails: List<String>, startDate: String, endDate: String, bookKey: String): Result<UiOutcomesSelectModel> {
        when (val data =
            bookDataSource.postBooksOutcomes(PostBooksOutcomesBody(userEmails, Duration(startDate, endDate), bookKey))) {
            is NetworkState.Success -> return Result.success(data.body.toUiOutcomesSelectModel())
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

    override suspend fun postSettlementAdd(
        bookKey: String,
        startDate: String,
        endDate: String,
        usersEmails: List<String>,
        outcomes: List<settleOutcomes>
    ):  Result<UiSettlementAddModel> {
        val outcomes = outcomes.map {
            com.aos.data.entity.request.book.Outcomes(it.outcome.toDouble(),it.userEmail)
        }
        when (val data =
            bookDataSource.postSettlementAdd(PostSettlementAddBody(bookKey, startDate, endDate, usersEmails, outcomes))) {
            is NetworkState.Success -> return Result.success(data.body.toPostSettlementAddModel())
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
    override suspend fun getSettlementSee(bookKey: String): Result<UiSettlementSeeModel> {
        when (val data =
            bookDataSource.getSettlementSee(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toUiSettlementSeeModel())
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
    override suspend fun getSettlementDetailSee(
        id: Long
    ):  Result<UiSettlementAddModel> {
        when (val data =
            bookDataSource.getSettlementDetailSee(id)) {
            is NetworkState.Success -> return Result.success(data.body.toPostSettlementAddModel())
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
    override suspend fun getBooksInfo(bookKey: String): Result<UiBookSettingModel> {
        when (val data =
            bookDataSource.getBooksInfo(bookKey)) {
            is NetworkState.Success -> return Result.success(data.body.toUiBookSettingModel())
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