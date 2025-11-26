package com.example.imagetextapp.repository

// repository/PostRepository.kt

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagetextapp.model.Post
import kotlinx.coroutines.flow.Flow

class PostRepository {
    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,  // 每页加载6个项目
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource() }
        ).flow
    }
}