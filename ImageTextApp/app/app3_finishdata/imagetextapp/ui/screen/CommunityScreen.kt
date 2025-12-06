package com.example.imagetextapp.ui.screen

import android.util.Log
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
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import com.example.imagetextapp.repository.PostRepository
import com.example.imagetextapp.ui.component.FeedCard
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.UUID

import com.example.imagetextapp.ui.screen.CommunityViewModel
@Composable
fun CommunityScreen() {
    val viewModel: CommunityViewModel = viewModel(
        factory = CommunityViewModelFactory()
    )
    val posts = viewModel.posts.collectAsLazyPagingItems()

//    val postRepository = remember { PostRepository() }
//    val posts = postRepository.getPosts().collectAsLazyPagingItems()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 下拉刷新状态
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val listState = rememberLazyGridState()

    // 监听刷新状态的变化
    LaunchedEffect(posts.loadState.refresh) {
        when (posts.loadState.refresh) {
            is androidx.paging.LoadState.Loading -> {
                // 如果是用户手动触发的刷新，保持isRefreshing为true
                if (!isRefreshing) {
                    // 这是初始加载，不显示下拉刷新指示器
                }
            }
            is androidx.paging.LoadState.NotLoading -> {
                isRefreshing = false
            }
            is androidx.paging.LoadState.Error -> {
                isRefreshing = false
                val error = (posts.loadState.refresh as androidx.paging.LoadState.Error).error
                Log.e("CommunityScreen", "Refresh error: $error")
            }
        }
    }

    // 监听加载更多状态的变化
    LaunchedEffect(posts.loadState.append) {
        when (posts.loadState.append) {
            is androidx.paging.LoadState.Error -> {
                val error = (posts.loadState.append as androidx.paging.LoadState.Error).error
                Log.e("CommunityScreen", "Append error: $error")
            }
            else -> {}
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            isRefreshing = true
            posts.refresh()
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    count = posts.itemCount,
                    key = { index ->
                        val post = posts[index]
                        // 修复：确保键唯一，添加索引作为后缀
                        "${post?.post_id ?: "null"}-$index"
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
                    } else {
                        // 加载中占位符
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(200.dp)
                                .fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                // 加载更多
                if (posts.loadState.append is androidx.paging.LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                }

                // 加载更多错误
                if (posts.loadState.append is androidx.paging.LoadState.Error) {
                    item {
                        val error = (posts.loadState.append as androidx.paging.LoadState.Error).error
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "加载失败: ${error.message?.take(20) ?: "未知错误"}",
                                    color = Color.Red
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = { posts.retry() }
                                ) {
                                    Text("重试")
                                }
                            }
                        }
                    }
                }
            }

            // 初始加载状态
            if (posts.loadState.refresh is androidx.paging.LoadState.Loading && posts.itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            }

            // 初始加载错误
            if (posts.loadState.refresh is androidx.paging.LoadState.Error && posts.itemCount == 0) {
                val error = (posts.loadState.refresh as androidx.paging.LoadState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "加载失败: ${error.message?.take(30) ?: "未知错误"}",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { posts.refresh() }
                        ) {
                            Text("重试")
                        }
                    }
                }
            }

            // 空状态
            if (posts.loadState.refresh is androidx.paging.LoadState.NotLoading
                && posts.itemCount == 0) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("暂无数据", color = Color.Gray)
                }
            }
        }
    }
}