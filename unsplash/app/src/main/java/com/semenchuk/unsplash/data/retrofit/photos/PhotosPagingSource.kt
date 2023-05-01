package com.semenchuk.unsplash.data.retrofit.photos

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.semenchuk.unsplash.data.retrofit.RetrofitService
import com.semenchuk.unsplash.data.retrofit.photos.models.photos.UnsplashPhotosItem

class PhotosPagingSource(private val retrofitService: RetrofitService) :
    PagingSource<Int, UnsplashPhotosItem>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhotosItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhotosItem> {

        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            retrofitService.getPhotos.send(
                authHeader = "Bearer lJHnRy25xm5uNzJzxneToLxaHHieIpL49vVHeUq1tdI",
                page = page,
                per_page = 10
            )
        }.fold(onSuccess = {
            val items = it.body()!!
            LoadResult.Page(
                data = items, prevKey = null, nextKey = if (items.isEmpty()) null else page + 1
            )
        }, onFailure = {
            Log.d("TAG", "load: $it")
            LoadResult.Error(it)
        })
    }

    companion object {
        const val FIRST_PAGE = 1
    }
}