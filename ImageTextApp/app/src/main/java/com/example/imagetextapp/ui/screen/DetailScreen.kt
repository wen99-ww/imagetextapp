package com.example.imagetextapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.imagetextapp.model.Author
import com.example.imagetextapp.model.Post
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    navController: NavController,
    post: Post
) {
    var isLiked by remember { mutableStateOf(post.is_liked) }
    var likeCount by remember { mutableStateOf(post.like_count) }
    var isFollowed by remember { mutableStateOf(false) }
    var isCollected by remember { mutableStateOf(false) }

    // 计算图片容器比例
    val aspectRatio = remember(post.clips.firstOrNull()) {
        val firstClip = post.clips.firstOrNull()
        if (firstClip != null) {
            val ratio = firstClip.width.toFloat() / firstClip.height.toFloat()
            // 限制在 3:4 ~ 16:9 之间
            min(max(ratio, 0.75f), 1.777f)
        } else {
            1f
        }
    }

    Scaffold(
        topBar = {
            TopAuthorSection(
                author = post.author,
                isFollowed = isFollowed,
                onFollowToggle = { isFollowed = !isFollowed },
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomInteractionBar(
                isLiked = isLiked,
                likeCount = likeCount,
                isCollected = isCollected,
                onLikeClick = {
                    isLiked = !isLiked
                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                },
                onShareClick = { /* 分享功能 */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 图片横滑容器
            ImagePagerSection(
                post = post,
                aspectRatio = aspectRatio
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 内容区域
            ContentSection(post = post)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAuthorSection(
    author: Author,
    isFollowed: Boolean,
    onFollowToggle: () -> Unit,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 作者头像
                AsyncImage(
                    model = author.avatar,
                    contentDescription = "作者头像",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = author.nickname,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))

                // 关注按钮
                Button(
                    onClick = onFollowToggle,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFollowed)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = if (isFollowed) "已关注" else "关注",
                        fontSize = 12.sp
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
            }
        },
        actions = {
            // 空actions，所有内容都在title中
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagePagerSection(
    post: Post,
    aspectRatio: Float
) {
    val pagerState = rememberPagerState(pageCount = { post.clips.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val clip = post.clips[page]

            // 支持加载状态和失败状态
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(clip.url)
                    .crossfade(true)
                    .build(),
                contentDescription = "作品图片",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Filled.Warning,
                                contentDescription = "加载失败",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "图片加载失败",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }

        // 进度条（多图时显示）
        if (post.clips.size > 1) {
            ProgressIndicator(
                pageCount = post.clips.size,
                currentPage = pagerState.currentPage,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun ProgressIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(
                        width = if (index == currentPage) 20.dp else 8.dp,
                        height = 4.dp
                    )
                    .background(
                        color = if (index == currentPage) Color.White else Color.White.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}

@Composable
fun ContentSection(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // 标题区 - 完整展示不截断
        Text(
            text = post.title ?: "无标题",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp),
            maxLines = Int.MAX_VALUE
        )

        // 正文区 - 支持话题词高亮和点击
        HighlightedText(
            text = post.content,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 发布日期
        Text(
            text = "发布于 ${formatDetailDate(post.create_time)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun HighlightedText(
    text: String,
    modifier: Modifier = Modifier
) {
    val hashtagRegex = "#[^\\s#]+".toRegex()
    val matches = hashtagRegex.findAll(text)

    if (matches.none()) {
        // 没有话题词，直接显示
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            maxLines = Int.MAX_VALUE
        )
        return
    }

    val annotatedString = buildAnnotatedString {
        var lastIndex = 0

        matches.forEach { matchResult ->
            // 添加普通文本
            append(text.substring(lastIndex, matchResult.range.first))

            // 添加高亮的话题词
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(matchResult.value)
            }

            lastIndex = matchResult.range.last + 1
        }

        // 添加剩余文本
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        lineHeight = 24.sp,
        maxLines = Int.MAX_VALUE,
        modifier = modifier.clickable {
            // 话题词点击处理 - 这里可以添加跳转到话题页面的逻辑
        }
    )
}

@Composable
fun BottomInteractionBar(
    isLiked: Boolean,
    likeCount: Int,
    isCollected: Boolean,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 快捷评论框
        OutlinedTextField(
            value = "",
            onValueChange = { },
            placeholder = { Text("说点什么...") },
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        Spacer(modifier = Modifier.width(12.dp))

        // 点赞按钮
        IconButton(onClick = onLikeClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                    contentDescription = "点赞",
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = likeCount.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 评论按钮 - 修复：使用正确的图标名称
        IconButton(
            onClick = { /* 评论功能 */ },
            enabled = false
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Outlined.ChatBubbleOutline,  // 修复：使用正确的聊天图标
                    contentDescription = "评论"
                )
                Text("评论", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 收藏按钮 - 修复：使用正确的书签图标
        IconButton(
            onClick = { /* 收藏功能 */ },
            enabled = false
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (isCollected) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,  // 修复：使用正确的图标
                    contentDescription = "收藏"
                )
                Text("收藏", style = MaterialTheme.typography.labelSmall)
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // 分享按钮
        IconButton(onClick = onShareClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.Share, contentDescription = "分享")
                Text("分享", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

// 详情页专用的日期格式化函数
fun formatDetailDate(timestamp: Long): String {
    val currentTime = System.currentTimeMillis() / 1000
    val diff = currentTime - timestamp

    return when {
        // 24小时内
        diff < 86400 -> {
            val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
            if (diff < 86400 && diff >= 82800) { // 昨天
                "昨天 ${sdf.format(java.util.Date(timestamp * 1000))}"
            } else {
                sdf.format(java.util.Date(timestamp * 1000))
            }
        }
        // 7天内
        diff < 604800 -> "${diff / 86400}天前"
        // 其他情况
        else -> {
            val sdf = java.text.SimpleDateFormat("MM-dd", java.util.Locale.getDefault())
            sdf.format(java.util.Date(timestamp * 1000))
        }
    }
}
//package com.example.imagetextapp.ui.screen
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.SpanStyle
//import androidx.compose.ui.text.buildAnnotatedString
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.withStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import coil.compose.SubcomposeAsyncImage
//import coil.request.ImageRequest
//import com.example.imagetextapp.model.Author
//import com.example.imagetextapp.model.Post
//import kotlin.math.max
//import kotlin.math.min
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun DetailScreen(
//    navController: NavController,
//    post: Post
//) {
//    var isLiked by remember { mutableStateOf(post.is_liked) }
//    var likeCount by remember { mutableStateOf(post.like_count) }
//    var isFollowed by remember { mutableStateOf(false) }
//    var isCollected by remember { mutableStateOf(false) }
//
//    // 计算图片容器比例
//    val aspectRatio = remember(post.clips.firstOrNull()) {
//        val firstClip = post.clips.firstOrNull()
//        if (firstClip != null) {
//            val ratio = firstClip.width.toFloat() / firstClip.height.toFloat()
//            // 限制在 3:4 ~ 16:9 之间
//            min(max(ratio, 0.75f), 1.777f)
//        } else {
//            1f
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAuthorSection(
//                author = post.author,
//                isFollowed = isFollowed,
//                onFollowToggle = { isFollowed = !isFollowed },
//                onBackClick = { navController.popBackStack() }
//            )
//        },
//        bottomBar = {
//            BottomInteractionBar(
//                isLiked = isLiked,
//                likeCount = likeCount,
//                isCollected = isCollected,
//                onLikeClick = {
//                    isLiked = !isLiked
//                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
//                },
//                onShareClick = { /* 分享功能 */ }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
//            // 图片横滑容器
//            ImagePagerSection(
//                post = post,
//                aspectRatio = aspectRatio
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 内容区域
//            ContentSection(post = post)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopAuthorSection(
//    author: Author,
//    isFollowed: Boolean,
//    onFollowToggle: () -> Unit,
//    onBackClick: () -> Unit
//) {
//    TopAppBar(
//        title = {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                // 作者头像
//                AsyncImage(
//                    model = author.avatar,
//                    contentDescription = "作者头像",
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clip(CircleShape)
//                )
//                Spacer(modifier = Modifier.width(12.dp))
//                Text(
//                    text = author.nickname,
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Medium
//                )
//                Spacer(modifier = Modifier.weight(1f))
//
//                // 关注按钮
//                Button(
//                    onClick = onFollowToggle,
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (isFollowed)
//                            MaterialTheme.colorScheme.onSurfaceVariant
//                        else
//                            MaterialTheme.colorScheme.primary
//                    ),
//                    shape = RoundedCornerShape(20.dp)
//                ) {
//                    Text(
//                        text = if (isFollowed) "已关注" else "关注",
//                        fontSize = 12.sp
//                    )
//                }
//            }
//        },
//        navigationIcon = {
//            IconButton(onClick = onBackClick) {
//                Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
//            }
//        },
//        actions = {
//            // 空actions，所有内容都在title中
//        }
//    )
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ImagePagerSection(
//    post: Post,
//    aspectRatio: Float
//) {
//    val pagerState = rememberPagerState(pageCount = { post.clips.size })
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(aspectRatio)
//    ) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxSize()
//        ) { page ->
//            val clip = post.clips[page]
//
//            // 支持加载状态和失败状态
//            SubcomposeAsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(clip.url)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "作品图片",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize(),
//                loading = {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(MaterialTheme.colorScheme.surfaceVariant),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                },
//                error = {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(MaterialTheme.colorScheme.errorContainer),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Icon(
//                                Icons.Filled.Warning,
//                                contentDescription = "加载失败",
//                                tint = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.size(48.dp)
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Text(
//                                text = "图片加载失败",
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    }
//                }
//            )
//        }
//
//        // 进度条（多图时显示）
//        if (post.clips.size > 1) {
//            ProgressIndicator(
//                pageCount = post.clips.size,
//                currentPage = pagerState.currentPage,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun ProgressIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        repeat(pageCount) { index ->
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = 2.dp)
//                    .size(
//                        width = if (index == currentPage) 20.dp else 8.dp,
//                        height = 4.dp
//                    )
//                    .background(
//                        color = if (index == currentPage) Color.White else Color.White.copy(alpha = 0.5f),
//                        shape = RoundedCornerShape(2.dp)
//                    )
//            )
//        }
//    }
//}
//
//@Composable
//fun ContentSection(post: Post) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        // 标题区 - 完整展示不截断
//        Text(
//            text = post.title ?: "无标题",
//            style = MaterialTheme.typography.headlineSmall,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 12.dp),
//            maxLines = Int.MAX_VALUE
//        )
//
//        // 正文区 - 支持话题词高亮和点击
//        HighlightedText(
//            text = post.content,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // 发布日期
//        Text(
//            text = "发布于 ${formatDetailDate(post.create_time)}",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}
//
//@Composable
//fun HighlightedText(
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    val hashtagRegex = "#[^\\s#]+".toRegex()
//    val matches = hashtagRegex.findAll(text)
//
//    if (matches.none()) {
//        // 没有话题词，直接显示
//        Text(
//            text = text,
//            style = MaterialTheme.typography.bodyLarge,
//            lineHeight = 24.sp,
//            maxLines = Int.MAX_VALUE
//        )
//        return
//    }
//
//    val annotatedString = buildAnnotatedString {
//        var lastIndex = 0
//
//        matches.forEach { matchResult ->
//            // 添加普通文本
//            append(text.substring(lastIndex, matchResult.range.first))
//
//            // 添加高亮的话题词
//            withStyle(
//                style = SpanStyle(
//                    color = MaterialTheme.colorScheme.primary,
//                    fontWeight = FontWeight.Medium
//                )
//            ) {
//                append(matchResult.value)
//            }
//
//            lastIndex = matchResult.range.last + 1
//        }
//
//        // 添加剩余文本
//        if (lastIndex < text.length) {
//            append(text.substring(lastIndex))
//        }
//    }
//
//    Text(
//        text = annotatedString,
//        style = MaterialTheme.typography.bodyLarge,
//        lineHeight = 24.sp,
//        maxLines = Int.MAX_VALUE,
//        modifier = modifier.clickable {
//            // 话题词点击处理 - 这里可以添加跳转到话题页面的逻辑
//        }
//    )
//}
//
//@Composable
//fun BottomInteractionBar(
//    isLiked: Boolean,
//    likeCount: Int,
//    isCollected: Boolean,
//    onLikeClick: () -> Unit,
//    onShareClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // 快捷评论框
//        OutlinedTextField(
//            value = "",
//            onValueChange = { },
//            placeholder = { Text("说点什么...") },
//            modifier = Modifier
//                .weight(1f)
//                .height(40.dp),
//            shape = RoundedCornerShape(20.dp),
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
//                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
//            )
//        )
//
//        Spacer(modifier = Modifier.width(12.dp))
//
//        // 点赞按钮
//        IconButton(onClick = onLikeClick) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.Favorite,
//                    contentDescription = "点赞",
//                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
//                )
//                Text(
//                    text = likeCount.toString(),
//                    style = MaterialTheme.typography.labelSmall
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        // 评论按钮 - 修复：使用正确的图标名称
//        IconButton(
//            onClick = { /* 评论功能 */ },
//            enabled = false
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    Icons.Outlined.Chat,
//                    // 修复：使用正确的聊天图标
//                    contentDescription = "评论"
//                )
//                Text("评论", style = MaterialTheme.typography.labelSmall)
//            }
//        }
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        // 收藏按钮 - 修复：使用正确的书签图标
//        IconButton(
//            onClick = { /* 收藏功能 */ },
//            enabled = false
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = if (isCollected) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,  // 修复：使用正确的图标
//                    contentDescription = "收藏"
//                )
//                Text("收藏", style = MaterialTheme.typography.labelSmall)
//            }
//        }
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        // 分享按钮
//        IconButton(onClick = onShareClick) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(Icons.Outlined.Share, contentDescription = "分享")
//                Text("分享", style = MaterialTheme.typography.labelSmall)
//            }
//        }
//    }
//}
//
//// 详情页专用的日期格式化函数
//fun formatDetailDate(timestamp: Long): String {
//    val currentTime = System.currentTimeMillis() / 1000
//    val diff = currentTime - timestamp
//
//    return when {
//        // 24小时内
//        diff < 86400 -> {
//            val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
//            if (diff < 86400 && diff >= 82800) { // 昨天
//                "昨天 ${sdf.format(java.util.Date(timestamp * 1000))}"
//            } else {
//                sdf.format(java.util.Date(timestamp * 1000))
//            }
//        }
//        // 7天内
//        diff < 604800 -> "${diff / 86400}天前"
//        // 其他情况
//        else -> {
//            val sdf = java.text.SimpleDateFormat("MM-dd", java.util.Locale.getDefault())
//            sdf.format(java.util.Date(timestamp * 1000))
//        }
//    }
//}
//package com.example.imagetextapp.ui.screen
//
//import androidx.compose.foundation.ExperimentalFoundationApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material.icons.filled.Share
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.example.imagetextapp.model.Post
//import com.example.imagetextapp.util.DateUtils
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
//@Composable
//fun DetailScreen(
//    navController: NavController,
//    post: Post
//) {
//    var isLiked by remember { mutableStateOf(post.is_liked) }
//    var likeCount by remember { mutableStateOf(post.like_count) }
//    var isFollowed by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
//                    }
//                },
//                actions = {
//                    // 作者信息和关注按钮
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        AsyncImage(
//                            model = post.author.avatar,
//                            contentDescription = "作者头像",
//                            modifier = Modifier
//                                .size(32.dp)
//                                .clip(CircleShape)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = post.author.nickname,
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
//                        Button(
//                            onClick = { isFollowed = !isFollowed },
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = if (isFollowed) MaterialTheme.colorScheme.onSurfaceVariant
//                                else MaterialTheme.colorScheme.primary
//                            )
//                        ) {
//                            Text(if (isFollowed) "已关注" else "关注")
//                        }
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            BottomInteractionBar(
//                isLiked = isLiked,
//                likeCount = likeCount,
//                onLikeClick = {
//                    isLiked = !isLiked
//                    likeCount = if (isLiked) likeCount + 1 else likeCount - 1
//                },
//                onShareClick = { /* 分享功能 */ }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
//            // 图片横滑容器
//            ImagePager(post = post)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // 内容区域
//            ContentSection(post = post)
//        }
//    }
//}
//
//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ImagePager(post: Post) {
//    val pagerState = rememberPagerState(pageCount = { post.clips.size })
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(3f / 4f)
//    ) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier.fillMaxSize()
//        ) { page ->
//            AsyncImage(
//                model = post.clips[page].url,
//                contentDescription = "作品图片",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize()
//            )
//        }
//
//        // 进度条（多图时显示）
//        if (post.clips.size > 1) {
//            ProgressIndicator(
//                pageCount = post.clips.size,
//                currentPage = pagerState.currentPage,
//                modifier = Modifier
//                    .align(Alignment.TopCenter)
//                    .padding(top = 16.dp)
//            )
//        }
//    }
//}
//
//@Composable
//fun ProgressIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        repeat(pageCount) { index ->
//            Box(
//                modifier = Modifier
//                    .padding(horizontal = 2.dp)
//                    .size(
//                        width = if (index == currentPage) 20.dp else 8.dp,
//                        height = 4.dp
//                    )
//                    .background(
//                        color = if (index == currentPage) Color.White else Color.White.copy(alpha = 0.5f),
//                        shape = MaterialTheme.shapes.small
//                    )
//            )
//        }
//    }
//}
//
//@Composable
//fun ContentSection(post: Post) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        // 标题
//        Text(
//            text = post.title ?: post.content,
//            style = MaterialTheme.typography.headlineSmall,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//
//        // 正文
//        Text(
//            text = post.content,
//            style = MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        // 发布日期
//        Text(
//            text = "发布于 ${DateUtils.formatDate(post.create_time)}",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}
//
//@Composable
//fun BottomInteractionBar(
//    isLiked: Boolean,
//    likeCount: Int,
//    onLikeClick: () -> Unit,
//    onShareClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // 快捷评论框
//        OutlinedTextField(
//            value = "",
//            onValueChange = { },
//            placeholder = { Text("说点什么...") },
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 16.dp),
//            shape = MaterialTheme.shapes.extraLarge
//        )
//
//        // 点赞按钮
//        IconButton(onClick = onLikeClick) {
//            Icon(
//                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
//                contentDescription = "点赞",
//                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
//            )
//        }
//        Text(text = likeCount.toString())
//
//        Spacer(modifier = Modifier.width(16.dp))
//
//        // 分享按钮
//        IconButton(onClick = onShareClick) {
//            Icon(Icons.Filled.Share, contentDescription = "分享")
//        }
//    }
//}