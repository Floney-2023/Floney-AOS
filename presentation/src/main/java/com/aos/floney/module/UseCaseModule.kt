package com.aos.floney.module

import com.aos.repository.BookRepository
import com.aos.repository.UserRepository
import com.aos.usecase.home.CheckUserBookUseCase
import com.aos.usecase.signup.CheckEmailCodeUseCase
import com.aos.usecase.signup.SendEmailUseCase
import com.aos.usecase.password.SendTempPasswordUseCase
import com.aos.usecase.signup.SignUpUseCase
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
    @Provides
    @Singleton
    fun provideSendTempPasswordUseCase(userRepository: UserRepository) = SendTempPasswordUseCase(userRepository)
    @Provides
    @Singleton
    fun provideCheckUserBookUseCase(bookRepository: BookRepository) = CheckUserBookUseCase(bookRepository)

}