package com.example.cookly.model

data class ChatRoomModel(
    val id: String = "",
    val participants: List<String> = listOf(),
    val lastMessage: String = "",
    val lastMsgTime: Long? = null,
)
