package com.example.quizler.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quizler.R
import com.example.quizler.components.InfoBanner
import com.example.quizler.components.SettingsItem
import com.example.quizler.components.SettingsItemGroup
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceL
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(modifier: Modifier, viewModel: SettingsViewModel = koinViewModel()) {
    val settingsItems by viewModel.settingsRow.collectAsStateWithLifecycle()
    Scaffold(
        snackbarHost = {
            InfoBanner(
                isActionButtonEnabled = false, // TODO: CHANGE
                data = null,
                onActionButtonClick = { }
            )
        }, content = { padding ->
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
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(spaceL))
                AppInfo()
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    verticalArrangement = Arrangement.spacedBy(spaceM)
                ) {
                    settingsItems.forEach {
                        when (it) {
                            is SettingsRow.Item -> SettingsItem(item = it)
                            is SettingsRow.ItemGroup -> SettingsItemGroup(group = it)
                            is SettingsRow.Title -> Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(id = it.titleStringRes),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = null)
                    Text(text = stringResource(R.string.logout))
                }
            }
        }
    }
    )
}

@Composable
private fun AppInfo() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spaceS)
    ) {
        Image(
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Kvizler", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Verzija 1.1.1", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    QuizlerTheme {
        Surface {
            SettingsScreen(modifier = Modifier.padding(spaceM))
        }
    }
}
