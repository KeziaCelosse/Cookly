package com.example.cookly.ui.event

import com.example.cookly.model.UserModel

sealed interface MainScreenAction {

    data class SelectUser(val userModel: UserModel) : MainScreenAction
    data class SendMessage(val message: String, val callBack: (status: Boolean) -> Unit): MainScreenAction

}