package com.example.quizler.ui.screen.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.components.Disclaimer
import com.example.quizler.components.InfoBanner
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceL
import com.example.quizler.theme.spaceM
import com.example.quizler.theme.spaceS
import com.example.quizler.ui.screen.home.plus
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateProfileScreen(
    navController: NavController = rememberNavController(),
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val nextScreenRoute by viewModel.gotoNextScreen.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = nextScreenRoute) {
        if (nextScreenRoute.isNotEmpty()) {
            navController.navigateAndForget(nextScreenRoute)
        }
    }

    Scaffold(snackbarHost = {
        InfoBanner(data = state.infoBannerData)
    }) { padding ->
        Column(modifier = Modifier.padding(padding + spaceM), verticalArrangement = Arrangement.Center) {
            EnterUsernameCard(
                username = state.username, avatar = state.choosenAvatar, onUsernameChange = viewModel::setUsername
            )
            Spacer(modifier = Modifier.size(spaceL))
            ChooseAvatarCard(
                modifier = Modifier.weight(1f), state.avatarList, state.choosenAvatar, viewModel::setAvatar
            )
            Spacer(modifier = Modifier.size(spaceL))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = viewModel::onConfirmCreateProfile
            ) {
                Text(text = stringResource(R.string.button_confirm_create_profile))
            }
        }
    }
}

@Composable
private fun EnterUsernameCard(
    username: String,
    avatar: Avatar? = null,
    onUsernameChange: (String) -> Unit = {}
) {
    Card {
        Column(
            modifier = Modifier.padding(spaceM),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.title_enter_username), style = MaterialTheme.typography.titleLarge
            )
            Disclaimer(
                dividerWidth = 3.dp, text = stringResource(R.string.disclaimer_enter_username)
            )
            TextField(
                modifier = Modifier.fillMaxWidth(), value = username, leadingIcon = {
                    if (avatar != null) {
                        Image(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(id = avatar.icon),
                            contentDescription = null
                        )
                    } else {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                }, onValueChange = onUsernameChange
            )
        }
    }
}

@Composable
private fun ChooseAvatarCard(
    modifier: Modifier = Modifier,
    avatarList: List<Avatar>,
    chosenAvatar: Avatar? = null,
    onChooseAvatar: (Avatar) -> Unit = {},
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(spaceM),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Izaberi avatar", style = MaterialTheme.typography.titleLarge)
            Disclaimer(
                dividerWidth = 3.dp, text = stringResource(R.string.disclaimer_choose_avatar)
            )
            Spacer(modifier = Modifier.size(spaceM))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(5.dp),
                horizontalArrangement = Arrangement.spacedBy(spaceS),
                verticalArrangement = Arrangement.spacedBy(spaceS)
            ) {
                items(items = avatarList) {
                    AvatarIcon(
                        avatar = it, isChoosen = chosenAvatar == it, onChooseAvatar = onChooseAvatar
                    )
                }
            }
        }
    }
}

@Composable
private fun AvatarIcon(
    modifier: Modifier = Modifier,
    avatar: Avatar,
    isChoosen: Boolean,
    onChooseAvatar: (Avatar) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(120.dp)
            .clickable(indication = null, interactionSource = interactionSource) {
                onChooseAvatar(avatar)
            }
    ) {
        if (isChoosen) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .border(5.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .clip(CircleShape), // make the image circular
                    painter = painterResource(id = avatar.icon), contentDescription = null
                )
            }
        } else {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .clip(CircleShape),
                painter = painterResource(id = avatar.icon),
                contentDescription = null
            )
        }

        // Draw avatar image
    }
}

data class Avatar(
    val icon: Int,
    val name: String
)

@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCreateProfileScreen() {
    QuizlerTheme {
        Surface {
            CreateProfileScreen()
        }
    }
}
