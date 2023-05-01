package com.semenchuk.unsplash.di

import android.app.Application
import com.semenchuk.unsplash.ui.home.HomeViewModelFactory
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
}