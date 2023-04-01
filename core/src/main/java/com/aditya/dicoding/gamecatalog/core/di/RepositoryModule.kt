package com.aditya.dicoding.gamecatalog.core.di

import com.aditya.dicoding.gamecatalog.core.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class, SettingPreferenceModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gameRepositoryImpl: com.aditya.dicoding.gamecatalog.core.data.GameRepositoryImpl): GameRepository
}