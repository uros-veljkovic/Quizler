package com.example.quizler.ui.screen.onboarding.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizler.R
import com.example.quizler.Screen
import com.example.quizler.components.InfoBanner
import com.example.quizler.components.Logo
import com.example.quizler.extensions.navigateAndForget
import com.example.quizler.model.InfoBannerData
import com.example.quizler.theme.QuizlerTheme
import com.example.quizler.theme.spaceXL
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    navController: NavController = rememberNavController(),
) {
    val state by viewModel.state.collectAsState()
    val progressAnimation by animateFloatAsState(
        targetValue = state.progress,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "Loading"
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData()
    }

    LaunchedEffect(key1 = state.isGoToHomeScreen) {
        if (state.isGoToHomeScreen) {
            delay(200L)
            navController.navigateAndForget(Screen.Home.route)
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
            LinearProgressIndicator(modifier = Modifier.layoutId("progress"), progress = progressAnimation)
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
