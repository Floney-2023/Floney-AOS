package com.aos.data.repository.remote.user

import com.aos.data.entity.request.user.DeleteWithdrawBody
import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PostCheckPasswordBody
import com.aos.data.entity.request.user.PostRecentBookkeySaveBody
import com.aos.data.entity.request.user.PutPasswordChangeBody
import com.aos.data.mapper.toDeleteWithdrawModel
import com.aos.data.mapper.toGetReceiveMarketing
import com.aos.data.mapper.toPostLoginModel
import com.aos.data.mapper.toPostSignUpUserModel
import com.aos.data.mapper.toUiMypageSearchModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.user.DeleteWithdrawModel
import com.aos.model.user.GetReceiveMarketingModel
import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel
import com.aos.model.user.UiMypageSearchModel
import com.aos.repository.UserRepository
import com.aos.util.NetworkState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override suspend fun postSignUpUser(
        email: String,
        nickname: String,
        password: String,
        receiveMarketing: Boolean,
    ): Result<PostSignUpUserModel> {
        when (val data =
            userRemoteDataSource.postSignUpUser(email, nickname, password, receiveMarketing)) {
            is NetworkState.Success -> return Result.success(data.body.toPostSignUpUserModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }

    override suspend fun getSendEmail(email: String): Result<Void?> {
        when (val data =
            userRemoteDataSource.getSendEmail(email)) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }

    override suspend fun postCheckEmailCode(email: String, code: String): Result<Void?> {
        when (val data =
            userRemoteDataSource.postCheckEmailCode(PostCheckEmailCodeBody(email, code))) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }

    override suspend fun getSendTempPassword(email: String): Result<Void?> {

        when (val data =
            userRemoteDataSource.getSendEmailPassword(email)) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }

    override suspend fun postLogin(email: String, password: String): Result<PostLoginModel> {
        when (val data =
            userRemoteDataSource.postLogin(email, password)) {
            is NetworkState.Success -> return Result.success(data.body.toPostLoginModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }
    override suspend fun putPasswordChange(newPassword: String, oldPassword: String): Result<Void?> {
        when (val data =
            userRemoteDataSource.putPasswordChange(PutPasswordChangeBody(newPassword, oldPassword))) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
    override suspend fun getNicknameChange(nickname: String): Result<Void?> {
        when (val data =
            userRemoteDataSource.getNicknameChange(nickname)) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
    override suspend fun putMarketingChange(agree: Boolean): Result<Void?> {
        when (val data =
            userRemoteDataSource.putMarketingChange(agree)) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
    override suspend fun getMarketingCheck(): Result<GetReceiveMarketingModel> {
        when (val data =
            userRemoteDataSource.getMarketingCheck()) {
            is NetworkState.Success -> {
                return Result.success(data.body.toGetReceiveMarketing())
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }
    override suspend fun getMypageSearch(): Result<UiMypageSearchModel> {
        when (val data =
            userRemoteDataSource.getMypageSearch()) {
            is NetworkState.Success -> {
                return Result.success(data.body.toUiMypageSearchModel())
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }
    override suspend fun getLogout(accessToken: String): Result<Void?> {

        when (val data =
            userRemoteDataSource.getLogout(accessToken)) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
    override suspend fun deleteWithdraw(accessToken: String, type: String, reason: String?): Result<DeleteWithdrawModel> {
        when (val data =
            userRemoteDataSource.deleteWithdraw(accessToken, DeleteWithdrawBody(type,reason))) {
            is NetworkState.Success -> return Result.success(data.body.toDeleteWithdrawModel())
            is NetworkState.Failure -> return Result.failure(
                RetrofitFailureStateException(data.error, data.code)
            )
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> return Result.failure(IllegalStateException("unKnownError"))
        }
    }
    override suspend fun postCheckPassword(password: String): Result<Void?> {

        when (val data =
            userRemoteDataSource.postCheckPassword(PostCheckPasswordBody(password))) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
    override suspend fun postRecentBookkeySave(bookKey: String): Result<Void?> {

        when (val data =
            userRemoteDataSource.postRecentBookkeySave(PostRecentBookkeySaveBody(bookKey))) {
            is NetworkState.Success -> {
                return Result.success(data.body)
            }
            is NetworkState.Failure -> {
                return Result.failure(
                    RetrofitFailureStateException(data.error, data.code)
                )
            }
            is NetworkState.NetworkError -> return Result.failure(IllegalStateException("NetworkError"))
            is NetworkState.UnknownError -> {
                return if(data.errorState == "body값이 null로 넘어옴") {
                    Result.success(null)
                } else {
                    Result.failure(IllegalStateException("unKnownError"))
                }
            }
        }
    }
}