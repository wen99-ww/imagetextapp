// repository/PagingDataSource.kt
package com.example.imagetextapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagetextapp.model.Post
import retrofit2.HttpException
import java.io.IOException

class PostPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 0
            val count = params.loadSize

            Log.d("PostPagingSource", "Loading page: $page, count: $count")

            val response = remoteDataSource.getFeed(count, page)

            if (response.status_code == 0) {
                val posts = response.post_list

                Log.d("PostPagingSource", "Loaded ${posts.size} posts, has_more: ${response.has_more}")

                // 确保posts列表不为null
                if (posts.isEmpty()) {
                    Log.d("PostPagingSource", "No more posts")
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = null
                    )
                }

                LoadResult.Page(
                    data = posts,
                    prevKey = if (page > 0) page - 1 else null,
                    nextKey = if (response.has_more == 1) page + 1 else null
                )
            } else {
                Log.e("PostPagingSource", "API error: ${response.status_code}")
                LoadResult.Error(Exception("API返回错误: ${response.status_code}"))
            }
        } catch (e: IOException) {
            Log.e("PostPagingSource", "IOException: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e("PostPagingSource", "HttpException: ${e.code()} - ${e.message()}")
            LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("PostPagingSource", "Exception: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}