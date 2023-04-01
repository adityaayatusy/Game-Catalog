package com.aditya.dicoding.gamecatalog.core.data.source.remote.network

import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("key") key : String,
        @Query("page_size") pageSize : Int,
        @Query("dates") date : String? = null,
        @Query("search") search : String? = null,
        @Query("ordering") ordering : String? = null,
    ): ListGameResponse

    @GET("games/{id}")
    suspend fun getDetailGame(
        @Path("id") id: Long,
        @Query("key") key : String
    ): GameResponse
}