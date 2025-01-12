package com.example.cookly.model

data class UserFirestore(
    val uid: String = "",
    val email: String = "",
    val profileUrl: String? = null,
    val username: String? = null
)
