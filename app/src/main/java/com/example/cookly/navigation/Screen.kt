package com.example.cookly.navigation

import com.example.cookly.ui.screen.KEY_ID_PRODUK
import com.example.cookly.ui.screen.key_chatId

sealed class Screen (val route: String) {

//    data object Home : Screen("mainScreen")
    data object Splash : Screen("splashScreen")
    data object Profile : Screen("profileScreen")
    data object Register : Screen("registerScreen")
    data object Login : Screen("loginScreen")
    data object Homepage: Screen("homepageScreen")
    data object FormBaru: Screen("detailScreen")
    data object Beverage: Screen("BeverageScreen")
    data object MainCourse: Screen("MainCourseScreen")
    data object Dessert: Screen("DessertScreen")
    data object Recipe: Screen("RecipeScreen")
    data object FriedRice: Screen("friedRice")
    data object VideoScreen: Screen("VideoScreen")
    data object ChatScreen: Screen("ChatScreen/{$key_chatId}") {
        fun withId(chatId: String) = "ChatScreen/$chatId"
    }
    data object MainCookScreen: Screen("MainCookScreen")
    data object MainChatScreen: Screen("MainChatScreen")
    data object SignUppScreen: Screen("SignUppScreen")
    data object SignInScreen: Screen("SignInScreen")
    data object Onboarding: Screen("Onboarding")
    data object SpanishGarlicChicken: Screen("SpanishGarlicChicken")
    data object CheeseCake: Screen("CheeseCake")
    data object MassConversionCalculator: Screen("MassConversionCalculator")
    data object FormEdit: Screen("detailScreen/{$KEY_ID_PRODUK}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}