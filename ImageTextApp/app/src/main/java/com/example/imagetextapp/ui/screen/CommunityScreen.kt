package com.example.imagetextapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import com.example.imagetextapp.repository.PostRepository
import com.example.imagetextapp.ui.component.FeedCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.UUID

@Composable
fun CommunityScreen() {
    val postRepository = remember { PostRepository() }
    val posts = postRepository.getPosts().collectAsLazyPagingItems()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 下拉刷新状态
    var refreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(refreshing)

    // 上拉加载更多检测
    val listState = rememberLazyGridState()
    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            lastVisibleItem >= totalItems - 5 && !posts.loadState.append.endOfPaginationReached
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && posts.loadState.append !is androidx.paging.LoadState.Loading) {
            posts.retry()
        }
    }

    // 处理刷新逻辑
    LaunchedEffect(refreshing) {
        if (refreshing) {
            posts.refresh()
            delay(1000) // 模拟网络延迟
            refreshing = false
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { refreshing = true },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(8.dp)
            ) {
                // 修复：使用itemsCount和手动遍历
                items(
                    count = posts.itemCount,
                    key = { index ->
                        posts.peek(index)?.post_id ?: UUID.randomUUID().toString()
                    }
                ) { index ->
                    val post = posts[index]
                    if (post != null) {
                        FeedCard(
                            post = post,
                            onItemClick = { clickedPost ->
                                Toast.makeText(
                                    context,
                                    "点击了: ${clickedPost.title ?: "无标题作品"}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onLikeClick = { likedPost ->
                                Toast.makeText(
                                    context,
                                    if (likedPost.is_liked) "取消点赞" else "点赞成功",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }

                // 加载更多指示器
                if (posts.loadState.append is androidx.paging.LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }

            // 空状态和错误状态处理
            if (posts.loadState.refresh is androidx.paging.LoadState.Error) {
                val error = (posts.loadState.refresh as androidx.paging.LoadState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "加载失败: ${error.message}",
                            color = Color.Red
                        )
                        Button(onClick = { posts.retry() }) {
                            Text("重试")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    CommunityScreen()
}