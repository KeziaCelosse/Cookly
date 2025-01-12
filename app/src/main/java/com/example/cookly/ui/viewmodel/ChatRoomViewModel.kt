package com.example.cookly.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.cookly.model.ChatRoomModel
import com.example.cookly.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ChatRoomViewModel: ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val _chatRooms = MutableStateFlow<List<ChatRoomModel>>(emptyList())
    val chatRooms: StateFlow<List<ChatRoomModel>> = _chatRooms.asStateFlow()

    fun createRoom(otherUserId: String, navController: NavHostController) {
        val currentUser = auth.currentUser?.uid
        if (currentUser == null) {
            Log.e("ChatRoomViewModel", "currentUser is null")
            return
        }

        val participants = listOf(currentUser, otherUserId).sorted()

        firestore.collection("chatRooms")
            .whereArrayContains("participants", currentUser)
            .get()
            .addOnSuccessListener { snapshot ->
                val existingRoom = snapshot.documents.firstOrNull { doc ->
                    val roomParticipants = doc.get("participants") as? List<*>
                    roomParticipants == participants
                }

                if(existingRoom != null) {
                    val chatRoom = existingRoom.toObject(ChatRoomModel::class.java)
                    if(chatRoom != null) {
                        navController.navigate(Screen.ChatScreen.withId(chatRoom.id))
                        Log.d("ChatRoomViewModel", "Existing room found: $chatRoom")
                    }
                }else {
                    val room = ChatRoomModel(
                        id = UUID.randomUUID().toString(),
                        participants = participants,
                    )

                    firestore.collection("chatRooms")
                        .document(room.id)
                        .set(room)
                        .addOnSuccessListener {
                            navController.navigate(Screen.ChatScreen.withId(room.id))
                            Log.d("ChatRoomViewModel", "New room created: $room")
                        }
                        .addOnFailureListener { e ->
                            Log.e("ChatRoomViewModel", "Error creating room", e)
                        }
                }

            }
            .addOnFailureListener {e ->
                Log.e("ChatRoomViewModel", "Error creating room", e)
            }

    }

}