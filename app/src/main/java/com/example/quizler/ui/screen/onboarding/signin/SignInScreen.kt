package com.example.quizler.ui.screen.onboarding.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quizler.MainScreen
import com.example.quizler.R
import com.example.quizler.components.InfoBanner
import com.example.quizler.components.Logo
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceS
import com.example.quizler.theme.spaceXL
import com.example.quizler.utils.signin.manager.GoogleSignInManager
import com.google.android.gms.common.api.ApiException
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = SignInState())
    LaunchedEffect(key1 = state.nextScreen) {
        state.nextScreen?.let { screen ->
            if (screen.isNotEmpty()) navController.navigateAndForget(screen)
        }
    }
    val launcher = rememberLauncherForActivityResult(contract = GoogleSignInManager()) { task ->
        try {
            task?.getResult(ApiException::class.java)?.idToken?.let { token ->
                viewModel.onSignInSuccessful(token)
            } ?: viewModel.onSignInFailed("No message received")
        } catch (e: ApiException) {
            viewModel.onSignInFailed(e.message.toString())
        }
    }
    Scaffold(
        snackbarHost = {
            InfoBanner(data = state.infoBannerData, isActionButtonVisible = false)
        },
        content = { padding ->
            SignInScreenContent(
                modifier = Modifier.padding(padding),
                onGoogleSignInButtonClick = {
                    launcher.launch(GoogleSignInManager.RequestCode)
                },
                onContinueAsGuestButtonClick = {
                    navController.navigateAndForget(MainScreen.Splash.route)
                }
            )
        }
    )
}

@Composable
private fun SignInScreenContent(
    modifier: Modifier = Modifier,
    onGoogleSignInButtonClick: () -> Unit = {},
    onContinueAsGuestButtonClick: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.default_background_pattern),
            contentScale = ContentScale.FillBounds,
            alpha = 0.2f,
            contentDescription = null
        )
        Card(
            modifier = Modifier
                .padding(spaceXL)
                .wrapContentSize(),
        ) {
            Column(
                modifier = Modifier
                    .padding(spaceXL)
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spaceS)
            ) {
                Logo(modifier = Modifier.size(100.dp))
                Text(
                    text = stringResource(R.string.text_log_in),
                    style = MaterialTheme.typography.titleLarge,
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffDB4437), contentColor = Color.White
                    ),
                    onClick = onGoogleSignInButtonClick
                ) {
                    Text(text = stringResource(R.string.button_google))
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff4267B2), contentColor = Color.White
                    ),
                    enabled = false, onClick = { /*TODO*/ }
                ) {
                    Text(text = stringResource(R.string.button_facebook))
                }
                Text(text = "ili")
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = onContinueAsGuestButtonClick
                ) {
                    Text(text = stringResource(R.string.button_continue_as_guest))
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignInScreen() {
    QuizlerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SignInScreenContent()
        }
    }
}
