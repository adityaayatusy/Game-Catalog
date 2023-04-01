package com.aditya.dicoding.gamecatalog.core.data.source.remote.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class GameResponse(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("rating")
    val rating: Float,

    @JsonProperty("background_image")
    val image: String? = null,

    @JsonProperty("description_raw")
    val descriptionRaw: String? = null,

    @JsonProperty("released")
    val released: Date,

    @JsonProperty("updated")
    val updated: Date,

    @JsonProperty("parent_platforms")
    val platforms: List<PlatformResponse>
)
