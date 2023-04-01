package com.aditya.dicoding.gamecatalog.core.data.source.remote.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ListGameResponse(
    @JsonProperty("results")
    val results: List<GameResponse>
)

