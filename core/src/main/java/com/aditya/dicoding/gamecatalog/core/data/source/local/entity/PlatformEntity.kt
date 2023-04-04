package com.aditya.dicoding.gamecatalog.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("unused", "unused", "unused", "unused", "unused")
@Entity(tableName = "platform")
data class PlatformEntity(
    @PrimaryKey(autoGenerate = false)
    var platformId: Long,
    @ColumnInfo(name = "name")
    var name: String,
)