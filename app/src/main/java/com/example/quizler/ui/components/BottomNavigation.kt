package com.example.quizler.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizler.R
import com.example.quizler.ui.screen.BottomNavigationConfig
import com.example.quizler.ui.theme.QuizlerTheme

@Composable
fun BottomNavigation(
    bottomNavigationConfig: BottomNavigationConfig,
    onSelectedItem: (BottomNavigationItem) -> Unit,
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "") },
            label = { Text(text = stringResource(id = R.string.home)) },
            selected = bottomNavigationConfig.itemSelected == BottomNavigationItem.Home,
            onClick = {
                onSelectedItem(BottomNavigationItem.Home)
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "") },
            label = { Text(text = stringResource(id = R.string.scoreboard)) },
            selected = bottomNavigationConfig.itemSelected == BottomNavigationItem.Scoreboard,
            onClick = {
                onSelectedItem(BottomNavigationItem.Scoreboard)
            }
        )
    }
}

@Preview
@Composable
fun PreviewBottomNavigation() {
    QuizlerTheme {
    }
}
