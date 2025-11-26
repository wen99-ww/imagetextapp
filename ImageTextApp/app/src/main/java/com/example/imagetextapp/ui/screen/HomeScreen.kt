//package com.example.imagetextapp.ui.screen
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.rememberLazyGridState
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.graphics.Color
//import androidx.paging.compose.collectAsLazyPagingItems
//import kotlinx.coroutines.delay
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import kotlinx.coroutines.launch
//import androidx.navigation.NavController
//import com.example.imagetextapp.repository.PostRepository
//import com.example.imagetextapp.ui.component.FeedCard
//import com.google.accompanist.swiperefresh.SwipeRefresh
//import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
//import java.util.UUID
//
//@Composable
//fun HomeScreen(navController: NavController? = null) {
//    val postRepository = remember { PostRepository() }
//    val posts = postRepository.getPosts().collectAsLazyPagingItems()
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//
//    // 添加调试信息
//    LaunchedEffect(posts.loadState) {
//        Log.d("HomeScreen", "Load state changed: ${posts.loadState}")
//    }
//
//    // 刷新状态管理
//    var refreshing by remember { mutableStateOf(false) }
//
//    // 根据Paging的加载状态同步刷新状态
//    LaunchedEffect(posts.loadState.refresh) {
//        val isLoading = posts.loadState.refresh is androidx.paging.LoadState.Loading
//        refreshing = isLoading
//        Log.d("HomeScreen", "Refresh state: $isLoading")
//    }
//
//    val swipeRefreshState = rememberSwipeRefreshState(refreshing)
//
//    // 上拉加载更多检测
//    val listState = rememberLazyGridState()
//    val shouldLoadMore = remember {
//        derivedStateOf {
//            val layoutInfo = listState.layoutInfo
//            val totalItems = layoutInfo.totalItemsCount
//            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
//
//            lastVisibleItem >= totalItems - 5 && !posts.loadState.append.endOfPaginationReached
//        }
//    }
//
//    LaunchedEffect(shouldLoadMore.value) {
//        if (shouldLoadMore.value && posts.loadState.append !is androidx.paging.LoadState.Loading) {
//            Log.d("HomeScreen", "Loading more data...")
//            posts.retry()
//        }
//    }
//
//    SwipeRefresh(
//        state = swipeRefreshState,
//        onRefresh = {
//            scope.launch {
//                Log.d("HomeScreen", "Manual refresh triggered")
//                posts.refresh()
//            }
//        },
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Box(modifier = Modifier.fillMaxSize()) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                modifier = Modifier.fillMaxSize(),
//                state = listState,
//                contentPadding = PaddingValues(8.dp)
//            ) {
//                items(
//                    count = posts.itemCount,
//                    key = { index ->
//                        posts.peek(index)?.post_id ?: UUID.randomUUID().toString()
//                    }
//                ) { index ->
//                    val post = posts[index]
//                    if (post != null) {
//                        FeedCard(
//                            post = post,
//                            onItemClick = { clickedPost ->
//                                // 点击跳转到详情页
//                                navController?.navigate("detail/${clickedPost.post_id}")
//                                Toast.makeText(
//                                    context,
//                                    "跳转到详情页: ${clickedPost.title ?: "无标题作品"}",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            },
//                            onLikeClick = { likedPost ->
//                                Toast.makeText(
//                                    context,
//                                    if (likedPost.is_liked) "取消点赞" else "点赞成功",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        )
//                    }
//                }
//
//                // 加载更多指示器
//                if (posts.loadState.append is androidx.paging.LoadState.Loading) {
//                    item {
//                        Box(
//                            modifier = Modifier.fillMaxWidth(),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//                        }
//                    }
//                }
//            }
//
//            // 空状态处理
//            if (posts.loadState.refresh is androidx.paging.LoadState.Loading && posts.itemCount == 0) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//                        Text("加载中...", modifier = Modifier.padding(8.dp))
//                    }
//                }
//            }
//
//            // 错误状态处理
//            if (posts.loadState.refresh is androidx.paging.LoadState.Error) {
//                val error = (posts.loadState.refresh as androidx.paging.LoadState.Error).error
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(
//                            text = "加载失败: ${error.message}",
//                            color = Color.Red
//                        )
//                        Button(
//                            onClick = {
//                                Log.d("HomeScreen", "Retry button clicked")
//                                posts.retry()
//                            }
//                        ) {
//                            Text("重试")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = null)
//}
package com.example.imagetextapp.ui.screen

// ui/screen/HomeScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableIntStateOf(3) } // 默认选中社区（索引3）

    val tabs = listOf("北京", "团购", "关注", "社区", "推荐")

    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部Tab栏
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
            edgePadding = 0.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            // 搜索图标
            IconButton(
                onClick = { /* 搜索功能 */ },
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "搜索",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 内容区域
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedTab) {
                3 -> CommunityScreen() // 社区页面
                else -> PlaceholderScreen("${tabs[selectedTab]}页面开发中...")
            }
        }
    }
}

// 在文件末尾添加PlaceholderScreen函数定义
@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

