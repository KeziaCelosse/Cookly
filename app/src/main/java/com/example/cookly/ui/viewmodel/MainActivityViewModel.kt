package com.example.cookly.ui.viewmodel

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.ui.input.key.key
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.model.UserModel
import com.example.cookly.ui.event.MainScreenAction
import com.example.cookly.ui.event.MainScreenEvent
import com.example.cookly.ui.util.SnackBarState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivityViewModel : ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val databaseRef = FirebaseDatabase.getInstance()

    private val _snackBarState = MutableStateFlow(SnackBarState())
    val snackBarState: StateFlow<SnackBarState> = _snackBarState.asStateFlow()

    val chatViewModel: ChatViewModel = ChatViewModel()

    private val _mainScreenEvent = MutableStateFlow(
        MainScreenEvent(
            currentUser = UserModel(
                mobileNumber = currentUser?.phoneNumber,
                userId = currentUser?.uid
            )
        )
    )
    val mainScreenEvent: StateFlow<MainScreenEvent> = _mainScreenEvent.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadUserList()
    }

    fun action(event: MainScreenAction) {
        Log.d("MainActivityViewModel", "action: event = $event")
        _isLoading.value = true
        when (event) {
            is MainScreenAction.SelectUser -> {
                Log.d("MainActivityViewModel", "action: SelectUser")
                initChat(event)
            }

            is MainScreenAction.SendMessage -> {
                Log.d("MainActivityViewModel", "action: SendMessage")
//                chatViewModel.sendMessage(event.message) { success ->
//                    _isLoading.value = false
//                    event.callBack(success)
//                }
            }

            else -> {
                Log.e("MainActivityViewModel", "action: Unknown event type: $event")
                _isLoading.value = false
            }
        }
    }

    private fun loadUserList() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (currentUser == null) {
                return@launch
            }
            databaseRef.getReference("users")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val list = mutableListOf<UserModel>()
                        Log.d("MainActivityViewModel", "onDataChange: ${snapshot.children.count()}")
                        for (userSnapshot in snapshot.children) {
                            Log.d(
                                "MainActivityViewModel",
                                "UserSnapshot: ${userSnapshot.key} - ${userSnapshot.value}"
                            )
                            for (child in userSnapshot.children) {
                                val valueClass = child.value?.javaClass?.name ?: "null"
                                Log.d(
                                    "MainActivityViewModel",
                                    "Child: ${child.key} - ${child.value} - $valueClass"
                                )
                            }
                            val userModel = userSnapshot.getValue(UserModel::class.java)
                            userModel?.let {
                                if (it.userId != currentUser.uid) {
                                    list.add(it)
                                }
                            }
                        }
                        Log.d("MainActivityViewModel", "onDataChange: ${list.toMutableList().size}")
                        _mainScreenEvent.value = _mainScreenEvent.value.copy(
                            userList = list.toMutableList()
                        )
                        _isLoading.value = false
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        _isLoading.value = false
                        _snackBarState.value = _snackBarState.value.copy(
                            show = true,
                            isError = true,
                            message = p0.message
                        )
                    }
                })
        }
    }

    private fun initChat(event: MainScreenAction.SelectUser) {
        _mainScreenEvent.value = _mainScreenEvent.value.copy(
            selectedUser = event.userModel
        )

//        try {
//            viewModelScope.launch(Dispatchers.IO) {
//                val dataSnapshot = databaseRef.getReference("chats").get().await()
//
////                if (dataSnapshot.hasChildren()) {
////                    try {
////                        _isLoading.value = false
////                        var chatId: String? = null
////
////                        for (chatSnapshot in dataSnapshot.children) {
////                            val chatUsers = chatSnapshot.child("users").value as? Map<*, *>
////                            if (chatUsers?.containsKey(event.userModel.userId) == true
////                                && chatUsers.containsKey(_mainScreenEvent.value.currentUser?.userId)) {
////                                chatId = chatSnapshot.key
////                                _mainScreenEvent.value = _mainScreenEvent.value.copy(
////                                    currentChatId = chatId
////                                )
//////                                chatViewModel.setChatId(chatId ?: "")
//////                                chatViewModel.getMessages { messages ->
//////                                    _mainScreenEvent.value = _mainScreenEvent.value.copy(
//////                                        messagesList = messages
//////                                    )
////                                }
////                                break
////                            }
////                        }
////
////                        if (chatId == null) {
////                            chatId = dataSnapshot.ref.push().key
////                            val chatData = mapOf(
////                                "${_mainScreenEvent.value.currentUser?.userId}" to true,
////                                "${event.userModel.userId}" to true,
////                            )
////                            dataSnapshot.ref.child(chatId ?: "").child("users").setValue(chatData)
////                            _mainScreenEvent.value = _mainScreenEvent.value.copy(
////                                currentChatId = chatId
////                            )
////                            chatViewModel.setChatId(chatId ?: "")
////                            chatViewModel.getMessages { messages ->
////                                _mainScreenEvent.value = _mainScreenEvent.value.copy(
////                                    messagesList = messages
////                                )
////                            }
////                        }
////                    } catch (ex: Exception) {
////                        Log.e("MainActivityViewModel", "Error in initChat", ex)
////                        _isLoading.value = false
////                        _snackBarState.value = _snackBarState.value.copy(
////                            show = true,
////                            isError = true,
////                            message = ex.message ?: "Error initializing chat"
////                        )
////                    }
////                } else {
////                    _isLoading.value = false
////                    val chatId = databaseRef.getReference("chats").push().key
////
////                    val chatData = mapOf(
////                        "${_mainScreenEvent.value.currentUser?.userId}" to true,
////                        "${event.userModel.userId}" to true,
////                    )
////                    dataSnapshot.ref.child(chatId ?: "").child("users").setValue(chatData)
////                    _mainScreenEvent.value = _mainScreenEvent.value.copy(
////                        currentChatId = chatId
////                    )
////                    chatViewModel.setChatId(chatId ?: "")
////                    chatViewModel.getMessages { messages ->
////                        _mainScreenEvent.value = _mainScreenEvent.value.copy(
////                            messagesList = messages
////                        )
////                    }
////                }
////            }
////        } catch (e: Exception) {
////            Log.e("MainActivityViewModel", "Error in initChat", e)
////            _isLoading.value = false
////            _snackBarState.value = _snackBarState.value.copy(
////                show = true,
////                isError = true,
////                message = e.message ?: "Error initializing chat"
////            )
////        }
////    }
//
////    override fun onCleared() {
////        super.onCleared()
////    }
    }
}