package com.example.quizler.ui.screen.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.ui.components.InfoBanner
import com.example.quizler.ui.components.Logo
import com.example.quizler.ui.model.InfoBannerData
import com.example.quizler.ui.screen.Screen
import com.example.quizler.ui.theme.QuizlerTheme
import com.example.quizler.ui.theme.spaceXL
import com.example.quizler.ui.utils.navigate
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = state.isGoToHomeScreen) {
        if (state.isGoToHomeScreen) {
            delay(200L)
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.default_background_pattern),
            contentScale = ContentScale.FillBounds,
            alpha = 0.2f,
            contentDescription = null
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(spaceXL),
            constraintSet = constraintSet,
        ) {
            Logo(modifier = Modifier.layoutId("logo"))
            LinearProgressIndicator(modifier = Modifier.layoutId("progress"), progress = state.progress)
            state.infoBannerData?.let {
                InfoBanner(
                    modifier = Modifier.layoutId("banner"),
                    isActionButtonEnabled = state.hasConnection && state.isDataFetchInProgress.not(),
                    data = it,
                    onActionButtonClick = viewModel::fetchData
                )
            }
        }
    }
}

val constraintSet = ConstraintSet {
    val logo = createRefFor("logo")
    val progress = createRefFor("progress")
    val banner = createRefFor("banner")

    constrain(logo) {
        centerHorizontallyTo(parent)
        centerVerticallyTo(parent)
    }
    constrain(progress) {
        centerHorizontallyTo(logo)
        top.linkTo(logo.bottom)
    }
    constrain(banner) {
        centerHorizontallyTo(parent)
        bottom.linkTo(parent.bottom)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSplashScreenLight() {
    QuizlerTheme {
        Column(
            modifier = Modifier.padding(spaceXL),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Logo()
            LinearProgressIndicator(progress = 0.2f)
            Spacer(modifier = Modifier.weight(1f))
            InfoBanner(data = InfoBannerData.ErrorLoadingContent)
        }
    }
}
