package com.example.cookly.ui.event

import com.example.cookly.model.MessageModel
import com.example.cookly.model.UserModel

data class MainScreenEvent(
    val currentUser: UserModel? = null,
    val selectedUser: UserModel? = null,
    val userList : MutableList<UserModel>? = null,
    val currentChatId : String? = null,
    val messagesList : List<MessageModel>? = null,
)