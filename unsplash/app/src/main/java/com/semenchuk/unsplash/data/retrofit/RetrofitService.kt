package com.semenchuk.unsplash.data.retrofit

import com.semenchuk.unsplash.data.retrofit.like.LikePhoto
import com.semenchuk.unsplash.data.retrofit.like.UnLikePhoto
import com.semenchuk.unsplash.data.retrofit.photoById.PhotoById
import com.semenchuk.unsplash.data.retrofit.photos.GetPhotos
import com.semenchuk.unsplash.data.retrofit.profile.UserProfile
import com.semenchuk.unsplash.data.retrofit.searchPhotos.SearchPhotos
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val getPhotos: GetPhotos = retrofit.create(GetPhotos::class.java)

    val searchPhotos: SearchPhotos = retrofit.create(SearchPhotos::class.java)

    val getPhotoById: PhotoById = retrofit.create(PhotoById::class.java)

    val likePhoto: LikePhoto = retrofit.create(LikePhoto::class.java)

    val unLikePhoto: UnLikePhoto = retrofit.create(UnLikePhoto::class.java)

    val userProfile: UserProfile = retrofit.create(UserProfile::class.java)


    companion object {
        const val BASE_URL = "https://api.unsplash.com"
    }
}