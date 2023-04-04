@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused")

package com.aditya.dicoding.gamecatalog.core.data.source.remote

import android.annotation.SuppressLint
import com.aditya.dicoding.gamecatalog.core.BuildConfig
import com.aditya.dicoding.gamecatalog.core.data.source.remote.network.ApiResponse
import com.aditya.dicoding.gamecatalog.core.data.source.remote.network.ApiService
import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("unused")
@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService)  {
    @SuppressLint("SimpleDateFormat")
    suspend fun getAllGame(): Flow<ApiResponse<List<GameResponse>>> {
        val currentDate = Calendar.getInstance()
        val parser = SimpleDateFormat("yyyy-MM-dd")
        val year = currentDate.get(Calendar.YEAR)
        return getGames(
            pageSize = 10,
            ordering = "-relevance",
            date = "$year-01-01,${parser.format(currentDate.time)}"
        )
    }

    suspend fun getTopGames(): Flow<ApiResponse<List<GameResponse>>> = getGames(
            pageSize = 5,
            ordering = "-relevance"

        )

    suspend fun searchGames(kewords: String): Flow<ApiResponse<List<GameResponse>>> = getGames(
        pageSize = 20,
        search = kewords
    )

    suspend fun gatDetailGame(id: Long): Flow<ApiResponse<GameResponse>> = flow {
        try {
            val response = apiService.getDetailGame(id, BuildConfig.API_KEY)
            emit(ApiResponse.Success(response))
        }catch (e: Exception){
            emit(ApiResponse.Error(e.toString()))
            Timber.e("gatDetailGame: ", e)
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getGames(pageSize : Int,
                         search : String? = null,
                         ordering : String? = null,
                         date: String? = null): Flow<ApiResponse<List<GameResponse>>> =
        flow {
            try {
                val response = apiService.getGames(
                    key = BuildConfig.API_KEY,
                    pageSize = pageSize,
                    ordering = ordering,
                    search = search,
                    date = date
                )

                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.e("getGames: ", e)
            }
        }.flowOn(Dispatchers.IO)
}