package com.aos.floney.module

import com.aos.data.repository.remote.book.BookRemoteDataSourceImpl
import com.aos.data.repository.remote.book.BookRepositoryImpl
import com.aos.data.repository.remote.user.UserRemoteDataSourceImpl
import com.aos.data.repository.remote.user.UserRepositoryImpl
import com.aos.repository.BookRepository
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

    @Singleton
    @Provides
    fun provideBookRepository(
        bookDataSourceImpl: BookRemoteDataSourceImpl
    ) : BookRepository {
        return BookRepositoryImpl(
            bookDataSourceImpl
        )
    }

}