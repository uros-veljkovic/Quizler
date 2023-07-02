package com.example.quizler.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quizler.MainScreen
import com.example.quizler.R
import com.example.quizler.components.InfoBanner
import com.example.quizler.components.SettingsButton
import com.example.quizler.components.SettingsButtonGroup
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceL
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import com.example.quizler.theme.spaceXXL
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    parentNavController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.signOut.collect {
            parentNavController.navigateAndForget(MainScreen.Empty.route)
        }
    }
    val context = LocalContext.current

    Scaffold(
        snackbarHost = {
            InfoBanner(
                isActionButtonEnabled = false,
                isActionButtonVisible = false,
                data = state.infoBannerData,
                onActionButtonClick = { }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.default_background_pattern),
                contentScale = ContentScale.FillBounds,
                alpha = 0.2f,
                contentDescription = null
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(spaceL))
                AppInfo()
                Spacer(modifier = Modifier.size(spaceL))
                Column(verticalArrangement = Arrangement.spacedBy(spaceM)) {
                    state.settingsItems.forEach { settingsItem ->
                        when (settingsItem) {
                            is SettingsItem.Button -> SettingsButton(button = settingsItem) {
                                viewModel.onSettingsItemClicked(it, context)
                            }

                            is SettingsItem.ButtonGroup -> SettingsButtonGroup(group = settingsItem) {
                                viewModel.onSettingsItemClicked(it, context)
                            }

                            is SettingsItem.Header -> Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = settingsItem.titleStringRes),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(spaceXXL))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    onClick = viewModel::onSignOut
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = null)
                    Text(text = stringResource(R.string.logout))
                }
            }
        }
    }
}

@Composable
private fun AppInfo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(spaceS),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .size(128.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Text(text = "Kvizler v1.1.1", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    QuizlerTheme {
        Surface {
//            SettingsScreen(modifier = Modifier.padding(spaceM))
        }
    }
}
