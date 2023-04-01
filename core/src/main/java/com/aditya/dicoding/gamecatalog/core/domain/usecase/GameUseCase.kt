package com.aditya.dicoding.gamecatalog.core.domain.usecase

import com.aditya.dicoding.gamecatalog.core.data.Resource
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGame(): Flow<Resource<List<GameModel>>>
    fun getTopGames(): Flow<Resource<List<GameModel>>>
    fun getFavoriteGame(): Flow<List<GameModel>>
    fun searchGames(keyword: String): Flow<Resource<List<GameModel>>>
    fun setFavoriteGame(gameModel: GameModel, state: Boolean, isSearch: Boolean = false)
    fun getThemeSetting(): Flow<Boolean>
    fun getDetailGame(gameModel: GameModel):Flow<Resource<GameModel>>
    fun getSearchDetailGame(gameModel: GameModel): Flow<Resource<GameModel>>
    suspend fun setThemeSetting(isDark: Boolean)
    suspend fun deleteAll()
}