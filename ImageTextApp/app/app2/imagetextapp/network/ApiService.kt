// network/ApiService.kt
package com.example.imagetextapp.network

import com.example.imagetextapp.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("feed/")
    fun getFeed(
        @Query("count") count: Int = 6,
        @Query("accept_video_clip") acceptVideoClip: Boolean = false
    ): Call<ApiResponse>
}