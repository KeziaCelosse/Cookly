package com.example.cookly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cookly.MainActivityNavigationNames.CHAT_SCREEN
import com.example.cookly.MainActivityNavigationNames.MAIN_SCREEN
import com.example.cookly.MainActivityNavigationNames.PROFILE_SCREEN
import com.example.cookly.MainActivityNavigationNames.SEARCH_SCREEN
import com.example.cookly.ui.screen.ChatScreen
import com.example.cookly.ui.screen.MainChatScreen
import com.example.cookly.ui.screen.ProfileChatScreen
import com.example.cookly.ui.screen.SearchScreen
import com.example.cookly.ui.viewmodel.MainActivityViewModel

@Composable
fun MainActivityNavigation(mainActivityViewModel: MainActivityViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = MAIN_SCREEN)
    {

        composable(MAIN_SCREEN){
            MainChatScreen(navController,
                mainActivityViewModel.mainScreenEvent.collectAsState(),
                mainActivityViewModel::action)
        }

        composable(CHAT_SCREEN){
            ChatScreen(
                chatId = it.arguments?.getString("chatId") ?: "",
                navController,
                mainActivityViewModel.mainScreenEvent.collectAsState(),
                mainActivityViewModel::action)
        }
        composable(PROFILE_SCREEN){
            ProfileChatScreen()
        }
        composable(SEARCH_SCREEN){
            SearchScreen()
        }

    }

}

object MainActivityNavigationNames {
    const val MAIN_SCREEN = "MAIN_SCREEN"
    const val CHAT_SCREEN = "CHAT_SCREEN"
    const val PROFILE_SCREEN = "PROFILE_SCREEN"
    const val SEARCH_SCREEN = "SEARCH_SCREEN"
}