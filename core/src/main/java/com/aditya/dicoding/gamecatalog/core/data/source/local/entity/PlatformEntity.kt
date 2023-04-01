package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platform")
data class PlatformEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var platformId: Long,
    @ColumnInfo(name = "name")
    var name: String,
)