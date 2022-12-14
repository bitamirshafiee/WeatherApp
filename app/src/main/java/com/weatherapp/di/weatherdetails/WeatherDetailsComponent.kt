package com.weatherapp.di.weatherdetails

import com.weatherapp.di.repository.RepositoryModule
import com.weatherapp.ui.weatherdetails.WeatherDetailsViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [WeatherDetailsModule::class, RepositoryModule::class])
@Singleton
interface WeatherDetailsComponent {

    @Component.Builder
    interface Builder {
        fun repositoryModule(repositoryModule: RepositoryModule): Builder
        fun build(): WeatherDetailsComponent
    }

    fun getViewModel(): WeatherDetailsViewModel

}