package com.aos.floney.module

import com.aos.data.repository.remote.UserRemoteDataSource
import com.aos.data.repository.remote.UserRemoteDataSourceImpl
import com.aos.data.repository.remote.UserRepositoryImpl
import com.aos.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userRemoteDataSource: UserRemoteDataSourceImpl
    ) : UserRepository {
        return UserRepositoryImpl(
            userRemoteDataSource
        )
    }

}