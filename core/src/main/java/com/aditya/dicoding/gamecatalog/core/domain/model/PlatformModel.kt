package com.aditya.dicoding.gamecatalog.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlatformModel(
    val id: Long,
    val name: String
): Parcelable
