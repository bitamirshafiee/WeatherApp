package com.weatherapp.di.repository

import com.weatherapp.BuildConfig
import com.weatherapp.repository.service.ServiceProvider
import com.weatherapp.repository.weather.WeatherRepository
import com.weatherapp.repository.weather.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBodyLogging(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttp(bodyLoggingInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(bodyLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideServiceProvider(retrofit: Retrofit): ServiceProvider = ServiceProvider(retrofit)

    @Provides
    @Singleton
    fun provideWeatherRepository(serviceProvider: ServiceProvider): WeatherRepository =
        WeatherRepositoryImpl(serviceProvider)


}