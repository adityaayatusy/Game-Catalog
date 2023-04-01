package com.aditya.dicoding.gamecatalog.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GameEntity
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.GamePlatformCrossRef
import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.PlatformEntity
import com.aditya.dicoding.gamecatalog.core.utils.Converters

@Database(entities = [GameEntity::class, PlatformEntity::class, GamePlatformCrossRef::class], version = 4, exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class GameDatabase:RoomDatabase() {
    abstract fun gameDao(): GameDao

}