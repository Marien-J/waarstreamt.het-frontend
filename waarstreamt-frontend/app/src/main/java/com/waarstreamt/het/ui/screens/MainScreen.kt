package com.waarstreamt.het.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.waarstreamt.het.ui.components.AppTopBar
import com.waarstreamt.het.ui.theme.Background
import com.waarstreamt.het.ui.theme.Muted
import com.waarstreamt.het.ui.theme.Surface
import com.waarstreamt.het.ui.theme.Teal

private data class TabItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

private val tabs = listOf(
    TabItem("Zoeken", Icons.Rounded.Search, Icons.Outlined.Search),
    TabItem("Verkennen", Icons.Rounded.GridView, Icons.Outlined.GridView),
    TabItem("Trending", Icons.Rounded.TrendingUp, Icons.Outlined.TrendingUp),
    TabItem("Opgeslagen", Icons.Rounded.Bookmark, Icons.Outlined.Bookmark),
)

@Composable
fun MainScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = Background,
        topBar = {
            AppTopBar(
                onLogoClick = { selectedTab = 0 },
                onSettingsClick = onNavigateToSettings,
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Surface,
                tonalElevation = 0.dp,
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index) tab.selectedIcon else tab.unselectedIcon,
                                contentDescription = tab.label,
                            )
                        },
                        label = { Text(tab.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Teal,
                            selectedTextColor = Teal,
                            unselectedIconColor = Muted,
                            unselectedTextColor = Muted,
                            indicatorColor = Teal.copy(alpha = 0.15f),
                        ),
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> HomeScreen(onResultClick = onNavigateToDetail)
                1 -> BrowseScreen()
                2 -> TrendingScreen()
                else -> SavedScreen()
            }
        }
    }
}
