package com.aos.data.repository.remote

import com.aos.data.mapper.toPostSignUpUserModel
import com.aos.data.util.RetrofitFailureStateException
import com.aos.model.PostSignUpUserModel
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
}