package com.example.cookly.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cookly.ui.event.MainScreenEvent
import com.example.cookly.ui.loginscreen.SignInScreen
import com.example.cookly.ui.onboarding.LogScreen
import com.example.cookly.ui.onboarding.OnboardingNavigation
import com.example.cookly.ui.onboarding.OnboardingNavigationObject
import com.example.cookly.ui.onboarding.OnboardingViewModel
import com.example.cookly.ui.onboarding.SignUpScreen
import com.example.cookly.ui.screen.BeverageScreen
import com.example.cookly.ui.screen.ChatScreen
import com.example.cookly.ui.screen.CheeseCake
import com.example.cookly.ui.screen.DessertScreen
import com.example.cookly.ui.screen.DetailScreen
import com.example.cookly.ui.screen.FriedRice
import com.example.cookly.ui.screen.HomePageScreen
import com.example.cookly.ui.screen.KEY_ID_PRODUK
import com.example.cookly.ui.screen.LoginScreen
import com.example.cookly.ui.screen.MainChatScreen
import com.example.cookly.ui.screen.MainCookScreen
import com.example.cookly.ui.screen.MainCourseScreen
import com.example.cookly.ui.screen.MassConversionCalculator
import com.example.cookly.ui.screen.ProfileScreen
import com.example.cookly.ui.screen.RecipeScreen
import com.example.cookly.ui.screen.RegistrationScreen
import com.example.cookly.ui.screen.SpanishGarlicChicken
import com.example.cookly.ui.screen.SplashScreen
import com.example.cookly.ui.screen.VideoScreen
import com.example.cookly.ui.screen.key_chatId
import com.example.cookly.ui.signupscreen.SignUppScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController(),
                  onboardingViewModel: OnboardingViewModel
) {
    NavHost (
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.MainCookScreen.route) {
            MainCookScreen(navController)
        }
        composable(route = Screen.Profile.route){
            ProfileScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegistrationScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Homepage.route) {
            HomePageScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Beverage.route) {
            BeverageScreen(navController)
        }
        composable(route = Screen.MainCourse.route) {
            MainCourseScreen(navController)
        }
        composable(route = Screen.Dessert.route) {
            DessertScreen(navController)
        }
        composable(route = Screen.Recipe.route) {
            RecipeScreen(navController)
        }
        composable(route = Screen.FriedRice.route) {
            FriedRice(navController)
        }
        composable(route = Screen.VideoScreen.route) {
            VideoScreen(navController)
        }
        composable(route = Screen.SignUppScreen.route) {
            SignUppScreen(navController)// Ganti dengan pemanggilan composable SignUpScreen
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(navController)
        }
        composable(route = Screen.SpanishGarlicChicken.route) {
            SpanishGarlicChicken(navController)
        }
        composable(route = Screen.CheeseCake.route) {
            CheeseCake(navController)
        }
        composable(route = Screen.MassConversionCalculator.route) {
            MassConversionCalculator(navController)
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingNavigation(onboardingViewModel = onboardingViewModel)
        }
        composable(
            route = Screen.ChatScreen.route,
            arguments = listOf(
                navArgument(key_chatId) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val chatId = navBackStackEntry.arguments?.getString(key_chatId)
            if (chatId != null) {
                ChatScreen(
                    chatId = chatId,
                    navController = navController,
                    mainScreenEvent = remember { mutableStateOf(MainScreenEvent()) }, // Pastikan event ini sesuai implementasi
                    action = { action ->

                    }
                )
            } else {
                Log.d("ChatScreen", "Chat ID is null")
            }
        }
        composable(route = Screen.MainChatScreen.route) {
            MainChatScreen (
                navController = navController,
                mainScreenEvent = remember { mutableStateOf(MainScreenEvent()) }, // Pastikan event ini sesuai implementasi
                action = { action ->
                    // Handle aksi di ChatScreen
                }
            )
        }




    }
}