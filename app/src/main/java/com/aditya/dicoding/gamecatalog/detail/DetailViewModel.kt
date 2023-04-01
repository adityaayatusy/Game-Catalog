package com.aditya.dicoding.gamecatalog.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {
    fun setFavoriteGame(game: GameModel, newStatus: Boolean, isSearch: Boolean) = gameUseCase.setFavoriteGame(game, newStatus, isSearch)
    fun getDetailGame(game: GameModel) = gameUseCase.getDetailGame(game).asLiveData()
    fun getSearchDetailGame(game: GameModel) = gameUseCase.getSearchDetailGame(game).asLiveData()
}

