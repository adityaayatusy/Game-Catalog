package com.aditya.dicoding.gamecatalog.di

import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGameUseCase(gameUseCaseImpl: GameUseCaseImpl): GameUseCase
}