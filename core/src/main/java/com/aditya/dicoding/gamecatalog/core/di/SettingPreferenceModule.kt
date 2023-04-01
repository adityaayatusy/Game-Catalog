package com.aditya.dicoding.gamecatalog.core.di

import android.content.Context
import com.aditya.dicoding.gamecatalog.core.data.source.local.preference.SettingPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingPreferenceModule {

    @Singleton
    @Provides
    fun provideSettingPreference(@ApplicationContext appContext: Context): SettingPreference =
        SettingPreference(
            appContext
        )
}