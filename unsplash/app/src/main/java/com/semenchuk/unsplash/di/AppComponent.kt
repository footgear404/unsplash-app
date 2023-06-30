package com.semenchuk.unsplash.di

import android.app.Application
import android.content.SharedPreferences
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
import com.semenchuk.unsplash.ui.detailedPhoto.DetailedPhotosViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        UiModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }

    fun homeViewModelFactory(): HomeViewModelFactory

    fun detailedPhotosViewModelFactory(): DetailedPhotosViewModelFactory

    fun sharedPrefs(): SharedPreferences
}