package com.aditya.dicoding.gamecatalog.core.data.source.remote.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PlatformResponse(
    @JsonProperty("platform")
    val platform: Platform
)

data class Platform(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String
)




