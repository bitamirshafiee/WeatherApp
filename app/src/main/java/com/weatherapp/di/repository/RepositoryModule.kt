package com.weatherapp.di.repository

import com.weatherapp.BuildConfig
import com.weatherapp.repository.service.ServiceProvider
import com.weatherapp.repository.weather.WeatherRepository
import com.weatherapp.repository.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideOkHttp(
    ): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideServiceProvider(retrofit: Retrofit): ServiceProvider = ServiceProvider(retrofit)

    @Provides
    @Singleton
    fun provideWeatherRepository(serviceProvider: ServiceProvider): WeatherRepository =
        WeatherRepositoryImpl(serviceProvider)


}