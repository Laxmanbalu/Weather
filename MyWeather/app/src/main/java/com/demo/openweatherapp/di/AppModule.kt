package com.demo.openweatherapp.di

import com.demo.openweatherapp.data.repoimpl.WeatherRepositoryImpl
import com.demo.openweatherapp.data.service.WeatherService
import com.demo.openweatherapp.domain.repo.WeatherRepository
import com.demo.openweatherapp.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
                .Builder()
                .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: WeatherRepositoryImpl): WeatherRepository = apiHelper

}