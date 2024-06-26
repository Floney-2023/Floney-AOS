package com.aos.data.repository.remote

import com.aos.data.entity.request.user.PostCheckEmailCode
import com.aos.data.mapper.toPostSignUpUserModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.PostSignUpUserModel
import com.aos.repository.UserRepository
import com.aos.util.NetworkState
import timber.log.Timber
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
            userRemoteDataSource.postCheckEmailCode(PostCheckEmailCode(email, code))) {
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