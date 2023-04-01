package com.aditya.dicoding.gamecatalog.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val gameUseCase: GameUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(gameUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}