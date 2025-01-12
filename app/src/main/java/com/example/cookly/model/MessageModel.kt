package com.example.cookly.model

data class MessageModel(
    val messageId: String = "",
    val senderId: String = "",
    val email: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val chatRoomId: String = "",
    val username: String = "",
)
