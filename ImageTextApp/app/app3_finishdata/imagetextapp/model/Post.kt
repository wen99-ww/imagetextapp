// model/Post.kt
package com.example.imagetextapp.model

data class Post(
    val post_id: String,
    val title: String? = null,  // 明确标注为可空
    val content: String,
    val create_time: Long,
    val author: Author,
    val clips: List<Clip>? = emptyList(),  // 修复：设为可空并提供默认值
    val music: Music? = null,  // 设为可空
    val hashtag: List<Hashtag>? = null,
    val like_count: Int = 0,
    val is_liked: Boolean = false
) {
    // 提供安全的辅助方法
    val safeClips: List<Clip>
        get() = clips ?: emptyList()
}

data class Author(
    val user_id: String,
    val nickname: String,
    val avatar: String
)

data class Clip(
    val type: Int,  // 0:图片, 1:视频
    val width: Int,
    val height: Int,
    val url: String
)

// 新增的音乐数据类
data class Music(
    val volume: Int,
    val seek_time: Int,
    val url: String
)

// 新增的话题标签数据类
data class Hashtag(
    val start: Int,
    val end: Int
)

// 新增API响应数据类
data class ApiResponse(
    val status_code: Int,
    val has_more: Int,
    val post_list: List<Post>
)