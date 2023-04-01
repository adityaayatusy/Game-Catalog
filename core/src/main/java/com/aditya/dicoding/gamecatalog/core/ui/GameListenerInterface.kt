package com.aditya.dicoding.gamecatalog.core.ui

import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel

interface GameListenerInterface {
    fun onDetailClicked(gameModel: GameModel)
}