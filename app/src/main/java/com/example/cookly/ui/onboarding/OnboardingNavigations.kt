package com.example.cookly.ui.onboarding

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

object OnboardingNavigationObject {
    const val ONBOARDING_SCREEN = "ONBOARDING_SCREEN"
    const val LOGIN_SCREEN = "LOGIN_SCREEN"
    const val SIGNUP_SCREEN = "SIGNUP_SCREEN"
    const val OTP_SCREEN = "OTP_SCREEN"
}

@Composable
fun OnboardingNavigation(onboardingViewModel: OnboardingViewModel) {

    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val snackBarState by onboardingViewModel.snackBarState.collectAsState()
    val context = LocalContext.current // Get the current context

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { paddingValues ->
        val it = paddingValues
        NavHost(
            navController = navController,
            startDestination = OnboardingNavigationObject.ONBOARDING_SCREEN,
            modifier = Modifier.padding(it)
        ) {
            composable(
                route = OnboardingNavigationObject.ONBOARDING_SCREEN
            ) {
                OnboardingScreen(
                    navController
                )
            }
            composable(
                route = OnboardingNavigationObject.LOGIN_SCREEN
            ) {
                LogScreen(
                    navController,
                    { event -> onboardingViewModel.action(event, context) } // Pass context here
                )
            }
            composable(
                route = OnboardingNavigationObject.SIGNUP_SCREEN
            ) {
                SignUpScreen(
                    navController,
                    { event -> onboardingViewModel.action(event, context) } // Pass context here
                )
            }
            composable(
                route = OnboardingNavigationObject.OTP_SCREEN
            ) {
                OtpScreen(
                    navController,
                    { event, ctx -> onboardingViewModel.action(event, ctx) } // Pass context here
                )
            }
        }

        LaunchedEffect(key1 = snackBarState.show) {
            scope.launch {
                if (snackBarState.show) {
                    snackBarHostState.showSnackbar(
                        message = snackBarState.message,
                        if (snackBarState.isError) "Error" else "Success",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}