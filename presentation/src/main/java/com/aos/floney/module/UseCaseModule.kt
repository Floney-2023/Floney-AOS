package com.aos.floney.module

import com.aos.data.repository.remote.UserRepositoryImpl
import com.aos.repository.UserRepository
import com.aos.usecase.CheckEmailCodeUseCase
import com.aos.usecase.SendEmailUseCase
import com.aos.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSendEmailUseCase(userRepository: UserRepository) = SendEmailUseCase(userRepository)
    @Provides
    @Singleton
    fun provideCheckEmailCodeUseCase(userRepository: UserRepository) = CheckEmailCodeUseCase(userRepository)
    @Provides
    @Singleton
    fun provideSignUpUseCase(userRepository: UserRepository) = SignUpUseCase(userRepository)

}