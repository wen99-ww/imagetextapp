
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