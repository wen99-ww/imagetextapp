// repository/PostRepository.kt
package com.example.imagetextapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagetextapp.model.Post
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
) {
    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                enablePlaceholders = false,
                initialLoadSize = 6,  // 添加初始加载大小
                prefetchDistance = 2  // 添加预加载距离
            ),
            pagingSourceFactory = { PostPagingSource(remoteDataSource) }
        ).flow
    }
}