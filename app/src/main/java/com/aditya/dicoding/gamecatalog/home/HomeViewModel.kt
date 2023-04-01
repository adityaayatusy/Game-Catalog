package com.aditya.dicoding.gamecatalog.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {
    val games = gameUseCase.getAllGame().asLiveData()
    val topGames = gameUseCase.getTopGames().asLiveData()
    val isDark = gameUseCase.getThemeSetting().asLiveData()
}