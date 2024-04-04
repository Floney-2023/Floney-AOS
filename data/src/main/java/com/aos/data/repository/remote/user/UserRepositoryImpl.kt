package com.aos.data.repository.remote.user

import com.aos.data.entity.request.user.PostCheckEmailCodeBody
import com.aos.data.entity.request.user.PutPasswordChangeBody
import com.aos.data.mapper.toPostLoginModel
import com.aos.data.mapper.toPostSignUpUserModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.user.PostLoginModel
import com.aos.model.user.PostSignUpUserModel
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
}