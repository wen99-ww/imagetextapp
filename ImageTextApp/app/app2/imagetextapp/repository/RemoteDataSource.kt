// repository/RemoteDataSource.kt
package com.example.imagetextapp.repository

import com.example.imagetextapp.model.ApiResponse
import com.example.imagetextapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class RemoteDataSource {
    suspend fun getFeed(count: Int, page: Int): ApiResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<ApiResponse> = RetrofitClient.apiService.getFeed(
                    count = count,
                    acceptVideoClip = false
                ).execute()

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status_code == 0) {
                        body
                    } else {
                        throw IOException("API returned error: ${body?.status_code}")
                    }
                } else {
                    throw IOException("HTTP error: ${response.code()}")
                }
            } catch (e: IOException) {
                throw e
            } catch (e: Exception) {
                throw IOException("Network error: ${e.message}")
            }
        }
    }
}