package com.valkotova.wishboxwithcompose.di

import com.valkotova.wishboxwithcompose.data.repositories.*
import com.valkotova.wishboxwithcompose.domain.Repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsAuthRepo(repo: AuthRepositoryImpl): AuthRepository

    @Singleton
    @Binds
    fun bindsUserRepo(repo: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindsWishRepo(repo: WishRepositoryImpl): WishRepository

    @Singleton
    @Binds
    fun bindsWishListRepo(repo: WishListRepositoryImpl): WishListRepository
}