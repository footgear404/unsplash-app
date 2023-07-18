package com.semenchuk.unsplash.data.retrofit.collections

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.semenchuk.unsplash.data.UnsplashRepository
import com.semenchuk.unsplash.data.retrofit.collections.models.CollectionsItem

class CollectionPagingSource(
    private val unsplashRepository: UnsplashRepository
) :
    PagingSource<Int, CollectionsItem>() {
    override fun getRefreshKey(state: PagingState<Int, CollectionsItem>): Int = FIRST_PAGE

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionsItem> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            unsplashRepository.getCollections(page)
        }.fold(onSuccess = {
            val items = it.body()!!
            Log.d("TAG", "load: $items")
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