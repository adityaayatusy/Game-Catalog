package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

data class GamePlatformModel(
    val games: List<GameEntity>,
    val platforms: List<PlatformEntity>,
    val gamesWithPlatform: List<GamePlatformCrossRef>,
)
