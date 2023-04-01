package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GameWithPlatforms(
    @Embedded val game: GameEntity,
    @Relation(
        parentColumn = "gameId",
        entity = PlatformEntity::class,
        entityColumn = "platformId",
        associateBy = Junction(
            value = GamePlatformCrossRef::class,
            parentColumn = "gameId",
            entityColumn = "platformId"
        )
    )
    val platforms: List<PlatformEntity>
)