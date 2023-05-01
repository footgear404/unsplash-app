package com.semenchuk.unsplash.di

import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.photos.PhotosPagingSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    fun provideUnsplashRepository(
        retrofitService: RetrofitService
    ): UnsplashRepository {
        return UnsplashRepository(retrofitService)
    }

    @Provides
    fun providePhotosPagingSource(retrofitService: RetrofitService): PhotosPagingSource {
        return PhotosPagingSource(retrofitService)
    }


    @Provides
    @Singleton
    fun provideRetrofitService(): RetrofitService {
        return RetrofitService()
    }
}