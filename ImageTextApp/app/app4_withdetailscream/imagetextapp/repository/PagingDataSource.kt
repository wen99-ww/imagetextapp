// repository/PagingDataSource.kt
package com.example.imagetextapp.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagetextapp.model.Post
import retrofit2.HttpException
import java.io.IOException

// repository/PagingDataSource.kt
class PostPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, Post>() {

    // 添加最大页数限制
    private var maxPages = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val page = params.key ?: 0
            val count = params.loadSize

            // 防止无限加载：设置最大页数为50
            if (page > 50) {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }

            Log.d("PostPagingSource", "Loading page: $page, count: $count")

            val response = remoteDataSource.getFeed(count, page)

            if (response.status_code == 0) {
                val posts = response.post_list

                // 修复：确保posts不为null
                val safePosts = posts ?: emptyList()

                Log.d("PostPagingSource", "Loaded ${safePosts.size} posts, has_more: ${response.has_more}")

                // 如果没有数据，停止分页
                if (safePosts.isEmpty()) {
                    Log.d("PostPagingSource", "No more posts available")
                    return LoadResult.Page(
                        data = emptyList(),
                        prevKey = if (page > 0) page - 1 else null,
                        nextKey = null
                    )
                }

                LoadResult.Page(
                    data = safePosts,
                    prevKey = if (page > 0) page - 1 else null,
                    // 修复：只有当有更多数据且不是最后一页时才加载下一页
                    nextKey = if (response.has_more == 1 && safePosts.isNotEmpty()) {
                        page + 1
                    } else null
                )
            } else {
                Log.e("PostPagingSource", "API error: ${response.status_code}")
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            Log.e("PostPagingSource", "Exception: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return null  // 简化刷新逻辑
    }
}
