package com.aos.floney.module

import com.aos.data.api.UserService
import com.aos.data.repository.remote.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSourceImpl(userService: UserService) = UserRemoteDataSourceImpl(userService)


}