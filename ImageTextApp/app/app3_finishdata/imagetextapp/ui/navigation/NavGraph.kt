
package com.example.imagetextapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.imagetextapp.model.Post
import com.example.imagetextapp.ui.screen.DetailScreen
import com.example.imagetextapp.ui.screen.HomeScreen
import com.example.imagetextapp.ui.screen.ProfileScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Detail : Screen("detail/{postId}") {
        fun createRoute(postId: String) = "detail/$postId"
    }
}

fun NavGraphBuilder.setupNavigation(navController: NavController) {
    composable(Screen.Home.route) {
        HomeScreen()  // 修复：传递navController参数
    }
    composable(Screen.Profile.route) {
        ProfileScreen()
    }
    composable(
        route = Screen.Detail.route,
        arguments = listOf(navArgument("postId") { type = NavType.StringType })
    ) { backStackEntry ->
        val postId = backStackEntry.arguments?.getString("postId") ?: ""
        // 这里需要从数据源获取对应的Post对象
        val post = getPostById(postId)
        if (post != null) {
            DetailScreen(
                navController = navController,  // 修复：传递navController参数
                post = post
            )
        } else {
            // 处理post为null的情况
            ErrorScreen(message = "作品不存在: $postId")
        }
    }
}

// 临时函数，后续需要从Repository获取真实数据
private fun getPostById(postId: String): Post? {
    // 这里应该从数据源获取，暂时返回null
    return null
}

// 错误页面组件
@androidx.compose.runtime.Composable
fun ErrorScreen(message: String) {
    androidx.compose.foundation.layout.Box(
        modifier = androidx.compose.ui.Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(text = message)
    }
}