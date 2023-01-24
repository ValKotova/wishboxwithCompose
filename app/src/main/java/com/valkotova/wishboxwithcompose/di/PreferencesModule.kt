package com.valkotova.wishboxwithcompose.di

import com.valkotova.wishboxwithcompose.data.repositories.PrefsRepositoryImpl
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface PreferencesModule {
    @Singleton
    @Binds
    fun bindsPreference(pref: PrefsRepositoryImpl): PrefsRepository
}