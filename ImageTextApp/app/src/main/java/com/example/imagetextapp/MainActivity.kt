//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//
//import com.example.imagetextapp.ui.screen.CommunityScreen
//
////├── src/main/java/com/example/imagetextapp/
////│   ├── ui/
////│   │   ├── screen/
////│   │   │   ├── MainScreen.kt          # 主界面
////│   │   │   ├── HomeScreen.kt          # 首页
////│   │   │   ├── CommunityScreen.kt     # 社区页面（双列瀑布流）
////│   │   │   ├── DetailScreen.kt        # 详情页
////│   │   │   └── ProfileScreen.kt       # 个人页面
////│   │   ├── component/
////│   │   │   ├── FeedCard.kt           # 作品卡片组件
////│   │   │   ├── ImagePager.kt         # 图片横滑组件
////│   │   │   ├── BottomNavigationBar.kt # 底部导航
////│   │   │   └── TopTabBar.kt          # 顶部Tab栏
////│   │   ├── theme/
////│   │   │   ├── Theme.kt              # 主题定义
////│   │   │   └── Color.kt              # 颜色定义
////│   │   └── navigation/
////│   │       └── NavGraph.kt           # 导航图
////│   ├── model/
////│   │   ├── Post.kt                   # 作品数据模型
////│   │   ├── Author.kt                 # 作者数据模型
////│   │   └── ApiResponse.kt            # API响应模型
////│   ├── repository/
////│   │   ├── PostRepository.kt         # 数据仓库
////│   │   └── LocalDataStore.kt         # 本地存储
////│   ├── network/
////│   │   ├── ApiService.kt             # 网络接口
////│   │   └── RetrofitClient.kt         # Retrofit客户端
////│   └── MainActivity.kt               # 主Activity
//
////class MainActivity : ComponentActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContent {
////            ImageTextAppTheme {
////                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
////                }
////            }
////        }
////    }
////}
//// MainActivity.kt
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // 修复：使用 setContent 而不是 setContentView
//        setContent {
//            ImageTextAppTheme {
//                // 直接显示社区页面（最简版本）
//                CommunityScreen()
//            }
//        }
//    }
//}
//
//// 预览函数（可选）
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ImageTextAppTheme {
//        CommunityScreen()
//    }
//}

// MainActivity.kt
//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Email  // 修复：使用Email图标代替Message
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import com.example.imagetextapp.ui.screen.CommunityScreen
//import com.example.imagetextapp.ui.screen.ProfileScreen
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ImageTextAppTheme {
//                MainApp()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainApp() {
//    var selectedTab by remember { mutableIntStateOf(0) }
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                selectedTab = selectedTab,
//                onTabSelected = { selectedTab = it }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)) {
//            when (selectedTab) {
//                0 -> CommunityScreen()  // 首页显示社区页面
//                4 -> ProfileScreen()    // 我页面
//                else -> PlaceholderScreen("页面开发中...")
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    NavigationBar {
//        // 定义底部导航项 - 修复Message图标引用
//        val navItems = listOf(
//            NavItem("首页", Icons.Filled.Home, 0),
//            NavItem("朋友", Icons.Filled.Person, 1),
//            NavItem("相机", Icons.Filled.Add, 2),
//            NavItem("消息", Icons.Filled.Email, 3),  // 修复：使用Email图标
//            NavItem("我", Icons.Filled.Person, 4)
//        )
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                selected = selectedTab == item.index,
//                onClick = { onTabSelected(item.index) },
//                icon = { Icon(item.icon, contentDescription = item.title) },
//                label = { Text(item.title) }
//            )
//        }
//    }
//}
//
//@Composable
//fun PlaceholderScreen(text: String) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = text, style = MaterialTheme.typography.headlineMedium)
//    }
//}
//
//// 导航项数据类
//data class NavItem(
//    val title: String,
//    val icon: ImageVector,
//    val index: Int
//)

//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.imagetextapp.ui.screen.HomeScreen
//import com.example.imagetextapp.ui.screen.ProfileScreen
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ImageTextAppTheme {
//                MainApp()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainApp() {
//    var selectedTab by remember { mutableIntStateOf(0) } // 默认选中首页
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                selectedTab = selectedTab,
//                onTabSelected = { selectedTab = it }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)) {
//            when (selectedTab) {
//                0 -> HomeScreen()  // 首页显示Tab栏和社区内容
//                4 -> ProfileScreen()    // 我页面
//                else -> PlaceholderScreen("页面开发中...")
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    NavigationBar {
//        // 定义底部导航项
//        val navItems = listOf(
//            NavItem("首页", Icons.Filled.Home, 0),
//            NavItem("朋友", Icons.Filled.Person, 1),
//            NavItem("相机", Icons.Filled.Add, 2),
//            NavItem("消息", Icons.Filled.Email, 3),
//            NavItem("我", Icons.Filled.Person, 4)
//        )
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                selected = selectedTab == item.index,
//                onClick = {
//                    // 只有首页和"我"页面支持点击
//                    if (item.index == 0 || item.index == 4) {
//                        onTabSelected(item.index)
//                    }
//                },
//                enabled = item.index == 0 || item.index == 4, // 只有首页和"我"可点击
//                icon = {
//                    Icon(
//                        item.icon,
//                        contentDescription = item.title,
//                        tint = if (item.index == 0 || item.index == 4) MaterialTheme.colorScheme.primary
//                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                    )
//                },
//                label = {
//                    Text(
//                        item.title,
//                        color = if (item.index == 0 || item.index == 4) MaterialTheme.colorScheme.primary
//                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun PlaceholderScreen(text: String) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(text = text, style = MaterialTheme.typography.headlineMedium)
//    }
//}
//
//// 导航项数据类
//data class NavItem(
//    val title: String,
//    val icon: ImageVector,
//    val index: Int
//)
//
//@Preview(showBackground = true)
//@Composable
//fun MainAppPreview() {
//    MainApp()
//}
//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.imagetextapp.ui.screen.HomeScreen
//import com.example.imagetextapp.ui.screen.ProfileScreen
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ImageTextAppTheme {
//                MainApp()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainApp() {
//    var selectedTab by remember { mutableIntStateOf(0) } // 默认选中首页
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                selectedTab = selectedTab,
//                onTabSelected = { selectedTab = it }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)) {
//            when (selectedTab) {
//                0 -> HomeScreen()  // 首页显示Tab栏和社区内容
//                1 -> PlaceholderScreen("朋友页面开发中...")  // 朋友页面
//                2 -> PlaceholderScreen("相机页面开发中...")  // 相机页面
//                3 -> PlaceholderScreen("消息页面开发中...")  // 消息页面
//                4 -> ProfileScreen()    // 我页面
//                else -> PlaceholderScreen("页面开发中...")
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    NavigationBar {
//        // 定义底部导航项
//        val navItems = listOf(
//            NavItem("首页", Icons.Filled.Home, 0),
//            NavItem("朋友", Icons.Filled.Person, 1),
//            NavItem("相机", Icons.Filled.Add, 2),
//            NavItem("消息", Icons.Filled.Email, 3),
//            NavItem("我", Icons.Filled.Person, 4)
//        )
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                selected = selectedTab == item.index,
//                onClick = {
//                    // 修改：所有页面都可以点击
//                    onTabSelected(item.index)
//                },
//                enabled = true, // 修改：所有页面都可点击
//                icon = {
//                    Icon(
//                        item.icon,
//                        contentDescription = item.title,
//                        tint = if (selectedTab == item.index)
//                            MaterialTheme.colorScheme.primary
//                        else
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                    )
//                },
//                label = {
//                    Text(
//                        item.title,
//                        color = if (selectedTab == item.index)
//                            MaterialTheme.colorScheme.primary
//                        else
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun PlaceholderScreen(text: String) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//        )
//    }
//}
//
//// 导航项数据类
//data class NavItem(
//    val title: String,
//    val icon: ImageVector,
//    val index: Int
//)
//
//@Preview(showBackground = true)
//@Composable
//fun MainAppPreview() {
//    MainApp()
//}

//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.imagetextapp.ui.screen.DetailScreen
//import com.example.imagetextapp.ui.screen.HomeScreen
//import com.example.imagetextapp.ui.screen.ProfileScreen
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ImageTextAppTheme {
//                MainApp()
//            }
//        }
//    }
//}
//
//@Composable
//fun MainApp() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "home"
//    ) {
//        composable("home") {
//            HomeScreen(navController = navController)
//        }
//        composable("detail/{postId}") { backStackEntry ->
//            val postId = backStackEntry.arguments?.getString("postId") ?: ""
//            // 这里需要根据postId获取Post对象
//            val post = getPostById(postId) // 需要实现这个函数
//            if (post != null) {
//                DetailScreen(navController = navController, post = post)
//            } else {
//                // 处理post为null的情况
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Text("作品不存在")
//                }
//            }
//        }
//    }
//}
//
//// 模拟函数：根据postId获取Post
//fun getPostById(postId: String): com.example.imagetextapp.model.Post? {
//    // 这里应该从数据源获取Post对象
//    // 暂时返回null，您需要根据实际情况实现
//    return null
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MainAppPreview() {
//    MainApp()
//}

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
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
    var selectedTab by remember { mutableIntStateOf(0) } // 默认选中首页

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> HomeScreen()  // 首页显示Tab栏和社区内容
                1 -> PlaceholderScreen("朋友页面开发中...")  // 朋友页面
                2 -> PlaceholderScreen("相机页面开发中...")  // 相机页面
                3 -> PlaceholderScreen("消息页面开发中...")  // 消息页面
                4 -> ProfileScreen()    // 我页面
                else -> PlaceholderScreen("页面开发中...")
            }
        }
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
                    // 修改：所有页面都可以点击
                    onTabSelected(item.index)
                },
                enabled = true, // 修改：所有页面都可点击
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
//package com.example.imagetextapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.imagetextapp.ui.screen.HomeScreen
//import com.example.imagetextapp.ui.screen.ProfileScreen
//import com.example.imagetextapp.ui.theme.ImageTextAppTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            ImageTextAppTheme {
//                MainApp()
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainApp() {
//    val navController = rememberNavController()  // 添加这行
//    var selectedTab by remember { mutableIntStateOf(0) } // 默认选中首页
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar(
//                selectedTab = selectedTab,
//                onTabSelected = { selectedTab = it }
//            )
//        }
//    ) { innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)) {
//            when (selectedTab) {
//                0 -> HomeScreen(navController = navController)  // 修复：传递navController参数
//                1 -> PlaceholderScreen("朋友页面开发中...")  // 朋友页面
//                2 -> PlaceholderScreen("相机页面开发中...")  // 相机页面
//                3 -> PlaceholderScreen("消息页面开发中...")  // 消息页面
//                4 -> ProfileScreen()    // 我页面
//                else -> PlaceholderScreen("页面开发中...")
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar(
//    selectedTab: Int,
//    onTabSelected: (Int) -> Unit
//) {
//    NavigationBar {
//        // 定义底部导航项
//        val navItems = listOf(
//            NavItem("首页", Icons.Filled.Home, 0),
//            NavItem("朋友", Icons.Filled.Person, 1),
//            NavItem("相机", Icons.Filled.Add, 2),
//            NavItem("消息", Icons.Filled.Email, 3),
//            NavItem("我", Icons.Filled.Person, 4)
//        )
//
//        navItems.forEach { item ->
//            NavigationBarItem(
//                selected = selectedTab == item.index,
//                onClick = {
//                    onTabSelected(item.index)
//                },
//                enabled = true,
//                icon = {
//                    Icon(
//                        item.icon,
//                        contentDescription = item.title,
//                        tint = if (selectedTab == item.index)
//                            MaterialTheme.colorScheme.primary
//                        else
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                    )
//                },
//                label = {
//                    Text(
//                        item.title,
//                        color = if (selectedTab == item.index)
//                            MaterialTheme.colorScheme.primary
//                        else
//                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                    )
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun PlaceholderScreen(text: String) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            style = MaterialTheme.typography.headlineMedium,
//            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//        )
//    }
//}
//
//// 导航项数据类
//data class NavItem(
//    val title: String,
//    val icon: ImageVector,
//    val index: Int
//)
//
//@Preview(showBackground = true)
//@Composable
//fun MainAppPreview() {
//    MainApp()
//}
