package com.aditya.dicoding.gamecatalog.core.utils

import com.aditya.dicoding.gamecatalog.core.data.source.local.entity.*
import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.PlatformResponse
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.domain.model.PlatformModel

object DataMapper {
    fun mapEntitiesToDomain(input: List<GameWithPlatforms>): List<GameModel> =
        input.map {
            GameModel(
                gameId = it.game.gameId,
                name = it.game.name,
                rating = it.game.rating,
                image = it.game.image,
                platforms = mapPlatFormEntityToModel(it.platforms),
                isFavorite = it.game.isFavorite,
                isTop = it.game.isTop,
                updated = it.game.updated,
                released = it.game.released,
                description = it.game.description
            )
        }

    fun mapEntitieToDomain(input: GameWithPlatforms): GameModel = GameModel(
        gameId = input.game.gameId,
        name = input.game.name,
        rating = input.game.rating,
        image = input.game.image,
        platforms = mapPlatFormEntityToModel(input.platforms),
        isFavorite = input.game.isFavorite,
        isTop = input.game.isTop,
        updated = input.game.updated,
        released = input.game.released,
        description = input.game.description
    )

    fun mapResponsesToDomain(input: List<GameResponse>): List<GameModel> =
        input.map {
            GameModel(
                gameId = it.id,
                name = it.name,
                rating = it.rating,
                image = it.image ?: "",
                platforms = mapPlatFormResponseToModel(it.platforms),
                isFavorite = false,
                isTop = false,
                updated = it.updated,
                released = it.released,
                description = it.descriptionRaw
            )
        }


    fun mapResponseToDomain(input: GameResponse): GameModel =
        GameModel(
            gameId = input.id,
            name = input.name,
            rating = input.rating,
            image = input.image ?: "",
            platforms = mapPlatFormResponseToModel(input.platforms),
            isFavorite = false,
            isTop = false,
            updated = input.updated,
            released = input.released,
            description = input.descriptionRaw
        )

    fun mapDomainToEntitie(input: GameModel, isFavorite: Boolean = false, isTop: Boolean = false): GamePlatformModel {
        val gameEntity = ArrayList<GameEntity>()
        val platformEntity = ArrayList<PlatformEntity>()
        val gameWithPlatform = ArrayList<GamePlatformCrossRef>()
        val game = GameEntity(
            gameId = input.gameId,
            name = input.name,
            rating = input.rating,
            image = input.image,
            isFavorite = isFavorite,
            isTop = isTop,
            updated = input.updated,
            released = input.released,
            description = input.description
        )

        gameEntity.add(game)

        input.platforms.map { item ->
            val platforms =
                PlatformEntity(
                    platformId = item.id,
                    name = item.name
                )

            platformEntity.add(platforms)
            gameWithPlatform.add(
                GamePlatformCrossRef(
                    input.gameId,
                    item.id
                )
            )
        }

        return GamePlatformModel(
            gameEntity,
            platformEntity,
            gameWithPlatform
        )
    }

    fun mapResponsesToEntities(input: List<GameResponse>, isTop: Boolean = false): GamePlatformModel {
        val gameEntity = ArrayList<GameEntity>()
        val platformEntity = ArrayList<PlatformEntity>()
        val gameWithPlatform = ArrayList<GamePlatformCrossRef>()
        input.map {
            val game = GameEntity(
                gameId = it.id,
                name = it.name,
                rating = it.rating,
                image = it.image ?: "",
                isFavorite = false,
                isTop = isTop,
                updated = it.updated,
                released = it.released,
                description = it.descriptionRaw
            )

            gameEntity.add(game)

            it.platforms.map { item ->
                val platforms =
                    PlatformEntity(
                        platformId = item.platform.id,
                        name = item.platform.name
                    )

                platformEntity.add(platforms)
                gameWithPlatform.add(
                    GamePlatformCrossRef(
                        it.id,
                        item.platform.id
                    )
                )
            }
        }

        return GamePlatformModel(
            gameEntity,
            platformEntity,
            gameWithPlatform
        )
    }

    private fun mapPlatFormResponseToModel(input: List<PlatformResponse>): List<PlatformModel> =
        input.map {
            PlatformModel(
                id = it.platform.id,
                name = it.platform.name
            )
        }

    private fun mapPlatFormEntityToModel(input: List<PlatformEntity>): List<PlatformModel> =
        input.map {
            PlatformModel(
                id = it.platformId,
                name = it.name
            )
        }

    fun mapDomainToEntity(input: GameModel) =
        GameEntity(
            gameId = input.gameId,
            name = input.name,
            description = input.description,
            rating = input.rating,
            image = input.image,
            isFavorite = input.isFavorite,
            isTop = input.isTop,
            updated = input.updated,
            released = input.released
        )
}