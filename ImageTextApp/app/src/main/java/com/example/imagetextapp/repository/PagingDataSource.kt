package com.example.imagetextapp.repository

// repository/PagingDataSource.kt


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagetextapp.model.Post

class PostPagingSource : PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            // 模拟分页加载数据
            val posts = LocalDataSource.getMockPosts().let { allPosts ->
                val start = page * pageSize
                val end = minOf(start + pageSize, allPosts.size)
                if (start < allPosts.size) {
                    allPosts.subList(start, end)
                } else {
                    emptyList()
                }
            }

            LoadResult.Page(
                data = posts,
                prevKey = if (page > 0) page - 1 else null,
                nextKey = if (posts.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}