package com.example.quizler.ui.screen.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.quizler.Screen
import com.example.quizler.ui.screen.onboarding.signin.SignInScreen
import org.koin.androidx.compose.koinViewModel

/**
 * TODO:
 *  Kreirati OnboardingScreen composable tako da prikazuje jedan od 4 subscreen-ova:
 *  1. SignInScreen - korisnik se loguje u aplikaciju i dobija token od signIn klijenta.
 *                    Desava se provera tokena na serveru, i server u zavisnosti od stanja u bazi salje rutu na koju treba da ide Kvizler aplikacija.
 *  2. UsernameScreen - Korisnik dolazi na ovaj ekran ukoliko je server u response body-u za singIn vration value "username_screen" za key "screen".
 *                      Ovde korisnik unosi svoje korisnicko ime koje se kasnije salje na server i tako se update-uje korisnikov profil u bazi.
 *                      Nakon ovog ekrana, korisnik bi obicno trebao da izabere avatar.
 *  3. AvatarScreen - Korisnik dolazi na ovaj ekran ukoliko je server u response body-u za signIn vratio value "avatar_screen" za key "screen".
 *                    Ovde korisnik bira svoj avatar koji se kasnije mappira u string name i salje na server i tako se update-uje korisnikov profil u bazi.
 *  4. SplashScreen - Postojeci ekran koji ucitava podatke. U request header-u je potrebno poslati token kako bi se fetch-ovali podaci sa servera. Ukoliko server vrati 401 (Unauthorized), vracamo korisnika na korak 1.
 */
@Composable
fun OnboardingScreen(navController: NavController, viewModel: OnboardingViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.gotoSplashScreen.collect {
            navController.navigate(Screen.Splash.route) {
                popUpTo(Screen.Splash.route)
            }
        }
    }

    when (state.visibleScreen) {
        OnboardingNavigation.Initial -> {
            // Show nothing
        }

        OnboardingNavigation.SignIn -> {
            SignInScreen(navController)
        }

        OnboardingNavigation.CreateProfileDetails -> TODO("Not implemented")
    }
}

