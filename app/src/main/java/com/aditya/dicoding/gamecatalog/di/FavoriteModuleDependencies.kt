package com.aditya.dicoding.gamecatalog.di

import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun gameUseCase(): GameUseCase
}