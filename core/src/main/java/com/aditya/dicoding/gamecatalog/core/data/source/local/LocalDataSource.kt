package com.aditya.dicoding.gamecatalog.core.data.source.local

import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GamePlatformModel
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GameWithPlatforms
import com.aditya.dicoding.gamecatalog.core.data.source.local.preference.SettingPreference
import com.aditya.dicoding.gamecatalog.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao, private val settingPreference: SettingPreference) {
    fun getAllGames(): Flow<List<GameWithPlatforms>> = gameDao.getGames()
    fun getTopGames(): Flow<List<GameWithPlatforms>> = gameDao.getTopGames()

    fun insertGame(
        gamePlatformModel: GamePlatformModel
    ) {
        gameDao.insertGame(gamePlatformModel.games)
        gameDao.insertPlatform(gamePlatformModel.platforms)
        gameDao.insertGameWithPlatform(gamePlatformModel.gamesWithPlatform)
    }

    fun getFavoriteGame(): Flow<List<GameWithPlatforms>> = gameDao.getFavoriteGames()

    fun getThemeSetting(): Flow<Boolean> = settingPreference.getThemeSetting()

    fun getDetailGame(id: Long): Flow<GameWithPlatforms> = gameDao.getDetailGame(id)

    suspend fun saveThemeSetting(isDark: Boolean){
        settingPreference.saveThemeSetting(isDark)
    }

    fun setFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateGame(game)
    }

    fun setDescriptionGame(game: GameEntity, newState: String? = null) {
        game.description = newState
        gameDao.updateGame(game)
    }

    fun deletaAllData(){
        gameDao.deleteGames()
        gameDao.deletePlatform()
        gameDao.deleteGamePlatformCrossRef()
    }

    fun isFavorite(id: Long): Boolean = gameDao.isFavorite(id)
}