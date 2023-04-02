package com.aditya.dicoding.gamecatalog.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(gameUseCase: GameUseCase): ViewModel() {
    val isDark = gameUseCase.getThemeSetting().asLiveData()
}