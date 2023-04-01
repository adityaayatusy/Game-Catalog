package com.aditya.dicoding.gamecatalog.core.domain.usecase

import com.aditya.dicoding.gamecatalog.core.data.Resource
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameUseCaseImpl @Inject constructor(
    private val gameRepository: GameRepository
): GameUseCase {
    override fun getAllGame(): Flow<Resource<List<GameModel>>> = gameRepository.getAllGame()
    override fun getTopGames(): Flow<Resource<List<GameModel>>> = gameRepository.getTopGames()
    override fun getFavoriteGame() = gameRepository.getFavoriteGame()
    override fun searchGames(keyword: String): Flow<Resource<List<GameModel>>> = gameRepository.searchGames(keyword)
    override fun setFavoriteGame(gameModel: GameModel, state: Boolean, isSearch: Boolean) = gameRepository.setFavoriteGame(gameModel, state, isSearch)
    override fun getThemeSetting(): Flow<Boolean> = gameRepository.getThemeSetting()
    override fun getDetailGame(gameModel: GameModel): Flow<Resource<GameModel>> = gameRepository.getDetailGame(gameModel)
    override fun getSearchDetailGame(gameModel: GameModel): Flow<Resource<GameModel>> = gameRepository.getSearchDetailGame(gameModel)
    override suspend fun setThemeSetting(isDark: Boolean) = gameRepository.setThemeSetting(isDark)
    override suspend fun deleteAll() = gameRepository.deleteAll()
}