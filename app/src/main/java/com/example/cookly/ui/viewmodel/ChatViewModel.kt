package com.example.cookly.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.model.MessageModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.math.log


class ChatViewModel : ViewModel() {

//    private val currentUser = FirebaseAuth.getInstance().currentUser
//    private val databaseRef = FirebaseDatabase.getInstance().reference
//    private var chatId: String? = null
//
//    fun action(){
//
//    }
//
//    fun setChatId(chatId : String){
//        this.chatId = chatId
//    }
//
//    fun getMessages(callBack: (list: List<MessageModel>) -> Unit) {
//        chatId?.let { id ->
//            viewModelScope.launch(Dispatchers.IO) {
//
//                databaseRef.child("chats").child(id).child("messages")
//                    .orderByChild("timestamp")
//                    .addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            val messages = dataSnapshot.children.mapNotNull { messageSnap ->
//                                messageSnap.getValue(MessageModel::class.java)
//                            }
//                            callBack(messages.reversed())
//                        }
//
//                        override fun onCancelled(databaseError: DatabaseError) {
//
//                        }
//
//                    })
//
//            }
//        }
//
//
//    }
//
//
//    fun sendMessage(message: String, callBack: (status: Boolean) -> Unit) {
//        var messageInstance = MessageModel(
//            text = message,
//            senderId = currentUser?.uid,
//            timestamp = System.currentTimeMillis()
//        )
//
//        chatId?.let { id ->
//            viewModelScope.launch {
//
//                withContext(Dispatchers.IO){
//                    val chatNode = databaseRef.child("chats")
//                    val messageId = chatNode.child(id).child("messages").push().key
//
//                    try {
//                        chatNode.child(id).child("messages")
//                            .child(messageId?:"")
//                            .setValue(messageInstance)
//                            .addOnSuccessListener {
//                                Log.d("TAG", "sendMessage: Success $message")
//                                callBack(true)
//                            }
//                            .addOnFailureListener{ exception ->
//                                callBack(false)
//                                Log.e("TAG", "sendMessage: ", exception)
//                            }
//
//                    } catch (ex : Exception){
//                        Log.e("TAG", "sendMessage: ", ex)
//                    }
//
//                }
//            }
//        }
//
//    }
//
//
//
//    override fun onCleared() {
//        super.onCleared()
//
//    }

    private val firestore = FirebaseFirestore.getInstance()
    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages.asStateFlow()

    fun observeMessages(roomId: String) {
        firestore.collection("messages")
            .whereEqualTo("chatRoomId", roomId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if(e != null) {
                    Log.e("message", "observeMessages: ", e)
                    return@addSnapshotListener
                }

                val messageList = snapshot?.toObjects(MessageModel::class.java) ?: emptyList()
                _messages.value = messageList
            }

    }

    fun sendMessage(message: String, userId: String, username: String, email: String, roomId: String) {
        val chatMessage = MessageModel(
            messageId = UUID.randomUUID().toString(),
            text = message,
            senderId = userId,
            email = email,
            timestamp = System.currentTimeMillis(),
            chatRoomId = roomId,
            username = username
        )

        firestore.collection("messages")
            .document(chatMessage.messageId)
            .set(chatMessage)
            .addOnSuccessListener {
                Log.d("message", "sendMessage: Success $message")
            }
            .addOnFailureListener { exception ->
                Log.e("message", "sendMessage: ", exception)
            }

    }
}
