package com.aditya.dicoding.gamecatalog.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aditya.dicoding.gamecatalog.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val gameUseCase: GameUseCase): ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return gameUseCase.getThemeSetting().asLiveData()
    }
    fun saveThemeSetting(isDark: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.setThemeSetting(isDark)
        }
    }

    fun deleteDatabas(){
        viewModelScope.launch(Dispatchers.IO) {
            gameUseCase.deleteAll()
        }
    }
}