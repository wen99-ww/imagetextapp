package com.example.imagetextapp.cache

// com/example/imagetextapp/cache/PostCache.kt
//package com.example.imagetextapp.cache

import com.example.imagetextapp.model.Post

object PostCache {
    private val postCache = mutableMapOf<String, Post>()

    fun cachePost(post: Post) {
        postCache[post.post_id] = post
    }

    fun getPost(postId: String): Post? {
        return postCache[postId]
    }

    fun clear() {
        postCache.clear()
    }
}