package com.aditya.dicoding.gamecatalog.core.data.source.local.room

import androidx.room.*
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GamePlatformCrossRef
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GameWithPlatforms
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.PlatformEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlatform(platforms: List<PlatformEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGameWithPlatform(join: List<GamePlatformCrossRef>)

    @Update
    fun updateGame(tourism: GameEntity)

    @Transaction
    @Query("SELECT * FROM games WHERE isTop = 0")
    fun getGames(): Flow<List<GameWithPlatforms>>

    @Transaction
    @Query("SELECT * FROM games WHERE isTop = 1")
    fun getTopGames(): Flow<List<GameWithPlatforms>>

    @Transaction
    @Query("SELECT * FROM games WHERE isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameWithPlatforms>>

    @Transaction
    @Query("SELECT * FROM games WHERE gameId=:id")
    fun getDetailGame(id: Long): Flow<GameWithPlatforms>

    @Query("DELETE FROM games")
    fun deleteGames()

    @Query("DELETE FROM platform")
    fun deletePlatform()

    @Query("DELETE FROM GamePlatformCrossRef")
    fun deleteGamePlatformCrossRef()

    @Query("SELECT EXISTS(SELECT * FROM games WHERE gameId = :id AND isFavorite = 1)")
    fun isFavorite(id: Long): Boolean

}