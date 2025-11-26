package com.example.imagetextapp.model

// model/Post.kt
// model/Post.kt

data class Post(
    val post_id: String,
    val title: String?,
    val content: String,
    val create_time: Long,  // 注意：使用下划线命名
    val author: Author,
    val clips: List<Clip>,
    val like_count: Int = 0,  // 使用下划线命名
    val is_liked: Boolean = false  // 使用下划线命名
)

data class Author(
    val user_id: String,
    val nickname: String,
    val avatar: String
)

data class Clip(
    val type: Int,
    val width: Int,
    val height: Int,
    val url: String
)