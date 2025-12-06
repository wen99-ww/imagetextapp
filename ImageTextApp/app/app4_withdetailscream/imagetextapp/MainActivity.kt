package com.example.imagetextapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.imagetextapp.ui.navigation.Screen
import com.example.imagetextapp.ui.navigation.setupNavigation
import com.example.imagetextapp.ui.screen.HomeScreen
import com.example.imagetextapp.ui.screen.ProfileScreen
import com.example.imagetextapp.ui.theme.ImageTextAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImageTextAppTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // 根据当前路由确定选中的Tab
    val selectedTab = when (currentRoute) {
        Screen.Home.route -> 0
        "friends" -> 1
        "camera" -> 2
        "messages" -> 3
        Screen.Profile.route -> 4
        else -> 0
    }

    Scaffold(
        bottomBar = {
            // 只在特定路由显示底部导航栏
            if (shouldShowBottomBar(navController)) {
                BottomNavigationBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tabIndex ->
                        when (tabIndex) {
                            0 -> {
                                navController.navigate(Screen.Home.route) {
                                    // 清除回退栈，只保留Home页面
                                    popUpTo(Screen.Home.route) { inclusive = true }
                                }
                            }
                            1 -> {
                                navController.navigate("friends") {
                                    // 跳转到朋友页面
                                    launchSingleTop = true
                                }
                            }
                            2 -> {
                                navController.navigate("camera") {
                                    // 跳转到相机页面
                                    launchSingleTop = true
                                }
                            }
                            3 -> {
                                navController.navigate("messages") {
                                    // 跳转到消息页面
                                    launchSingleTop = true
                                }
                            }
                            4 -> {
                                navController.navigate(Screen.Profile.route) {
                                    // 跳转到我的页面
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier.fillMaxSize()
    ) {
        setupNavigation(navController)

        // 添加其他页面的路由
        composable("friends") {
            PlaceholderScreen("朋友页面开发中...")
        }
        composable("camera") {
            PlaceholderScreen("相机页面开发中...")
        }
        composable("messages") {
            PlaceholderScreen("消息页面开发中...")
        }
    }
}

/**
 * 判断是否应该显示底部导航栏
 * 在详情页等二级页面不显示底部导航栏
 */
@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // 在这些页面不显示底部导航栏
    val hideBottomBarRoutes = listOf(
        "detail",  // 详情页
        // 可以添加其他不需要底部导航栏的页面
    )

    return hideBottomBarRoutes.none { route ->
        currentRoute?.contains(route) == true
    }
}

@Composable
fun BottomNavigationBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar {
        // 定义底部导航项
        val navItems = listOf(
            NavItem("首页", Icons.Filled.Home, 0),
            NavItem("朋友", Icons.Filled.Person, 1),
            NavItem("相机", Icons.Filled.Add, 2),
            NavItem("消息", Icons.Filled.Email, 3),
            NavItem("我", Icons.Filled.Person, 4)
        )

        navItems.forEach { item ->
            NavigationBarItem(
                selected = selectedTab == item.index,
                onClick = {
                    onTabSelected(item.index)
                },
                enabled = true,
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title,
                        tint = if (selectedTab == item.index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = if (selectedTab == item.index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            )
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// 导航项数据类
data class NavItem(
    val title: String,
    val icon: ImageVector,
    val index: Int
)

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    MainApp()
}