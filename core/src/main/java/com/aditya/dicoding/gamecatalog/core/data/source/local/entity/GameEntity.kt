package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "gameId")
    var gameId: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "rating")
    var rating: Float,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "isTop")
    var isTop: Boolean = false,
    @ColumnInfo(name = "updated")
    var updated: Date,
    @ColumnInfo(name = "released")
    var released: Date,
)
