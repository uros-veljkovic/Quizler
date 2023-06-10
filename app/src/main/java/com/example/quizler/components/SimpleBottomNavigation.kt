package com.example.quizler.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.model.BottomNavigationItem
import com.example.quizler.theme.QuizlerTheme

@Composable
fun SimpleBottomNavigation(
    navController: NavController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        BottomNavigationItem.values().forEach { item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = "") },
                label = { Text(text = stringResource(id = item.titleResId)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigateAndForget(item.route)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigation() {
    QuizlerTheme {
        SimpleBottomNavigation()
    }
}
