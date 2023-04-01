package com.aditya.dicoding.gamecatalog.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GameModel(
    val gameId: Long,
    val name: String,
    val rating: Float,
    val image: String,
    var description: String? = null,
    val platforms: List<PlatformModel>,
    var isFavorite: Boolean = false,
    var isTop: Boolean = false,
    val updated: Date,
    val released: Date
):Parcelable
