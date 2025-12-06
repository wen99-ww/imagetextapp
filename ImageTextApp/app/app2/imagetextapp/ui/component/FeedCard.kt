package com.example.imagetextapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.imagetextapp.model.Post

@Composable
fun FeedCard(
    post: Post,
    onItemClick: (Post) -> Unit = {},
    onLikeClick: (Post) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f)
            .padding(4.dp)
            .clickable { onItemClick(post) },  // 添加点击事件
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // 封面图片
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AsyncImage(
                    model = post.clips.firstOrNull()?.url ?: "",
                    contentDescription = "作品封面",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 底部信息区域
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                // 标题（最多2行）
                Text(
                    text = post.title ?: post.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // 作者信息和点赞
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 作者头像
                    AsyncImage(
                        model = post.author.avatar,
                        contentDescription = "作者头像",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )

                    // 作者名称
                    Text(
                        text = post.author.nickname,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    )

                    // 点赞区域
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { onLikeClick(post) },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                imageVector = if (post.is_liked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                                contentDescription = "点赞",
                                tint = if (post.is_liked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = formatLikeCount(post.like_count),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

// 工具函数：格式化点赞数
private fun formatLikeCount(count: Int): String {
    return when {
        count >= 10000 -> "${count / 1000}k"
        count >= 1000 -> "${count / 1000}.${(count % 1000) / 100}k"
        else -> count.toString()
    }
}