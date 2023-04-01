package com.aditya.dicoding.gamecatalog.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase

class FavoriteViewModel(gameUseCase: GameUseCase): ViewModel(){
    val favoriteGame = gameUseCase.getFavoriteGame().asLiveData()
}