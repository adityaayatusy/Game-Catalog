package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

import androidx.room.Entity
@Suppress("unused")
@Entity(primaryKeys = ["gameId", "platformId"])
data class GamePlatformCrossRef(
    val gameId: Long,
    val platformId: Long,
)