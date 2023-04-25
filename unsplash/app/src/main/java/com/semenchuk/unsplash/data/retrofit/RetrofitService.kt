package com.semenchuk.unsplash.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val getPhotos: GetPhotos = retrofit.create(GetPhotos::class.java)


    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }
}