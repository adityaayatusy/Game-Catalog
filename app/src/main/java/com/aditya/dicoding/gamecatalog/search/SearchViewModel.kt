package com.aditya.dicoding.gamecatalog.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {
    fun search(keywords: String) = gameUseCase.searchGames(keywords).asLiveData()
    val isDark = gameUseCase.getThemeSetting().asLiveData()
}