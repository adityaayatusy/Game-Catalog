package com.aditya.dicoding.gamecatalog.core.di

import com.aditya.dicoding.gamecatalog.core.BuildConfig
import com.aditya.dicoding.gamecatalog.core.data.source.remote.network.ApiService
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient{
        val logging = if(BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        }else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val mapper = ObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}