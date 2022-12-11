package com.weatherapp.di.repository

import com.weatherapp.BuildConfig
import com.weatherapp.di.scope.RepositoryScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RepositoryModule {

    @Provides
    @RepositoryScope
    fun provideOkHttp(
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }


    @Provides
    @RepositoryScope
    fun provideRetrofit(okHttpClient : OkHttpClient) =
        Retrofit.Builder().baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

}