package com.example.cookly.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cookly.model.UserFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class UserViewModel: ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val _users = MutableStateFlow<List<UserFirestore>>(emptyList())
    val users = _users

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        firestore.collection("users")
            .addSnapshotListener { snapshot, e ->
                if(e != null) {
                    return@addSnapshotListener
                }

                val userList = snapshot?.toObjects(UserFirestore::class.java) ?: emptyList()
                _users.value = userList

            }
    }

}