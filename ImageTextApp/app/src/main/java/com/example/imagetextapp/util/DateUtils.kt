package com.example.imagetextapp.util

// util/DateUtils.kt

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun formatDate(timestamp: Long): String {
        val currentTime = System.currentTimeMillis() / 1000
        val diff = currentTime - timestamp

        return when {
            diff < 60 -> "刚刚"
            diff < 3600 -> "${diff / 60}分钟前"
            diff < 86400 -> "${diff / 3600}小时前"
            diff < 604800 -> "${diff / 86400}天前"
            else -> {
                val sdf = SimpleDateFormat("MM-dd", Locale.getDefault())
                sdf.format(Date(timestamp * 1000))
            }
        }
    }
}