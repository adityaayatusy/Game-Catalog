package com.aditya.dicoding.gamecatalog.core.data

import com.aditya.dicoding.gamecatalog.core.data.source.local.LocalDataSource
import com.aditya.dicoding.gamecatalog.core.data.source.remote.RemoteDataSource
import com.aditya.dicoding.gamecatalog.core.data.source.remote.network.ApiResponse
import com.aditya.dicoding.gamecatalog.core.data.source.remote.response.GameResponse
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.domain.repository.GameRepository
import com.aditya.dicoding.gamecatalog.core.utils.AppExecutors
import com.aditya.dicoding.gamecatalog.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): GameRepository {
    override fun getAllGame(): Flow<Resource<List<GameModel>>> =
        object: NetworkBoundResource<List<GameModel>, List<GameResponse>>(){
            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gamePlatformModel = DataMapper.mapResponsesToEntities(data)
                appExecutors.diskIO().execute { localDataSource.insertGame(gamePlatformModel) }
            }

            override fun loadFromDB(): Flow<List<GameModel>> =
                localDataSource.getAllGames().map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGame()

            override fun shouldFetch(data: List<GameModel>?): Boolean = data == null || data.isEmpty()
        }.asFlow()

    override fun getTopGames(): Flow<Resource<List<GameModel>>> =
        object: NetworkBoundResource<List<GameModel>, List<GameResponse>>(){
            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gamePlatformModel = DataMapper.mapResponsesToEntities(data, true)
                appExecutors.diskIO().execute { localDataSource.insertGame(gamePlatformModel) }
            }

            override fun loadFromDB(): Flow<List<GameModel>> =
                localDataSource.getTopGames().map {
                    DataMapper.mapEntitiesToDomain(it)
                }

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getTopGames()

            override fun shouldFetch(data: List<GameModel>?): Boolean = data == null || data.isEmpty()
        }.asFlow()

    override fun searchGames(keyword: String): Flow<Resource<List<GameModel>>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.searchGames(keyword).first()) {
            is ApiResponse.Success -> {
                emit(
                    Resource.Success(
                        DataMapper.mapResponsesToDomain(apiResponse.data).map {gameModel ->
                           appExecutors.diskIO().execute { gameModel.isFavorite = localDataSource.isFavorite(gameModel.gameId) }
                            gameModel
                        }
                    )
                )
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(emptyList()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getDetailGame(gameModel: GameModel): Flow<Resource<GameModel>> =
        object: NetworkBoundResource<GameModel, GameResponse>(){
            override fun loadFromDB(): Flow<GameModel> = localDataSource.getDetailGame(gameModel.gameId).map {
                DataMapper.mapEntitieToDomain(it)
            }
            override suspend fun createCall(): Flow<ApiResponse<GameResponse>> = remoteDataSource.gatDetailGame(gameModel.gameId)

            override suspend fun saveCallResult(data: GameResponse) {
                val entity = DataMapper.mapDomainToEntity(gameModel)
                appExecutors.diskIO().execute { localDataSource.setDescriptionGame(entity, data.descriptionRaw) }
            }

            override fun shouldFetch(data: GameModel?): Boolean = data?.description == null

        }.asFlow()

    override fun getSearchDetailGame(gameModel: GameModel): Flow<Resource<GameModel>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = remoteDataSource.gatDetailGame(gameModel.gameId).first()) {
            is ApiResponse.Success -> {
                val data = DataMapper.mapResponseToDomain(apiResponse.data).apply {
                    appExecutors.diskIO().execute { isFavorite = localDataSource.isFavorite(gameId) }
                }

                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(gameModel))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getFavoriteGame(): Flow<List<GameModel>> = localDataSource.getFavoriteGame().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun setFavoriteGame(gameModel: GameModel, state: Boolean, isSearch: Boolean) {
        appExecutors.diskIO().execute{
            if (isSearch){
                val gamePlatformModel = DataMapper.mapDomainToEntitie(gameModel, state)
                localDataSource.insertGame(gamePlatformModel)
            }else{
                val gameEntity = DataMapper.mapDomainToEntity(gameModel)
                localDataSource.setFavoriteGame(gameEntity, state)
            }
        }
    }

    override fun getThemeSetting(): Flow<Boolean> = localDataSource.getThemeSetting()

    override suspend fun setThemeSetting(isDark: Boolean) {
        localDataSource.saveThemeSetting(isDark)
    }

    override suspend fun deleteAll() {
        appExecutors.diskIO().execute{ localDataSource.deletaAllData() }
    }


}