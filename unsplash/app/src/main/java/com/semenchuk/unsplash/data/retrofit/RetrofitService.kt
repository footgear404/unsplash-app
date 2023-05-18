package com.semenchuk.unsplash.data.retrofit

import com.semenchuk.unsplash.data.retrofit.photos.GetPhotos
import com.semenchuk.unsplash.data.retrofit.searchPhotos.SearchPhotos
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val getPhotos: GetPhotos = retrofit.create(GetPhotos::class.java)

    val searchPhotos: SearchPhotos = retrofit.create(SearchPhotos::class.java)


    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }
}