package com.example.cookly.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.model.MessageModel
import com.example.cookly.model.UserModel
import com.example.cookly.ui.event.MainScreenAction
import com.example.cookly.ui.event.MainScreenEvent
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.util.ImageCircle
import com.example.cookly.ui.util.WidthSpacer
import com.example.cookly.ui.util.formatTimestamp
import com.example.cookly.ui.viewmodel.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

const val key_chatId = "chatId"

@Composable
fun ChatScreen(
    chatId: String,
    navController: NavHostController,
    mainScreenEvent: State<MainScreenEvent>,
    action: (MainScreenAction) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val viewModel: ChatViewModel = viewModel()
    val messages by viewModel.messages.collectAsState()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current

    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val currentUser = auth.currentUser
    val selectedUser = mainScreenEvent.value.selectedUser

    Log.d("ChatScreen", "currentUser?.uid: ${currentUser?.uid}")
    Log.d("ChatScreen", "selectedUser?.uid: ${selectedUser?.userId}")

    // Observe messages when chatId changes
    LaunchedEffect(chatId) {
        viewModel.observeMessages(chatId)
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header Chat
        HeaderSection(
            selectedUser = selectedUser,
            onBackPressed = { backDispatcher?.onBackPressedDispatcher?.onBackPressed() }
        )

        // Daftar Pesan
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true,
            state = listState
        ) {
            items(messages.reversed()) { message ->
                ChatMessageItem(
                    message = message,
                    isOwnMessage = message.senderId == currentUser?.uid,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
        }

        // Scroll otomatis ke pesan terbaru
        LaunchedEffect(messages) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(0)
            }
        }

        // Input Pesan
        InputSection(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendClick = {
                if (messageText.isNotBlank() && currentUser != null) {
                    viewModel.sendMessage(
                        message = messageText,
                        userId = currentUser.uid,
                        email = currentUser.email.orEmpty(),
                        roomId = chatId,
                        username = currentUser.displayName.orEmpty()
                    )
                    messageText = "" // Reset hanya setelah mengirim
                } else {
                    Log.e("ChatScreen", "Cannot send message, user or message is invalid")
                }
            }
        )
    }
}

@Composable
fun HeaderSection(selectedUser: UserModel?, onBackPressed: () -> Unit) {
    Column(
        modifier = Modifier.background(Color(0xFFd44000)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.clickable { onBackPressed() }
                )
                WidthSpacer()
                ImageCircle(size = 45.dp)
                WidthSpacer(width = 15.dp)
                Text(
                    text = selectedUser?.username ?:"",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.dot_menu_icon),
                contentDescription = "",
                tint = Color.White
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color.White
        )
    }
}

@Composable
fun InputSection(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageChange,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xffd44000),
                focusedBorderColor = Color(0xffd44000),
                focusedTextColor = Color.Black
            ),
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(25.dp),
            label = {
                Text(text = "Type message", color = Color.Gray)
            }
        )

        WidthSpacer(15.dp)

//        Button(
//            onClick = onSendClick,
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.White,
//                contentColor = Color.White
//            ),
//            modifier = Modifier.size(70.dp),
//            shape = RoundedCornerShape(5.dp)
//        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "",
                tint = Color(0xFFD44000),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onSendClick() }
            )
        }
    }



@Composable
fun ChatMessageItem(
    message: MessageModel,
    isOwnMessage: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = if (isOwnMessage) Alignment.End else Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .widthIn(max = 340.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isOwnMessage) Color(0xFFD44000) else Color(0xffFFEFCF)
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (isOwnMessage) Color.White else Color.Black
                )
                Text(
                    text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(message.timestamp),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.End),
                    color = if (isOwnMessage) Color(0xffFFEFCF) else Color.Gray
                )
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SendChatItem(item: MessageModel) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Batasi lebar maksimal pesan
                .align(Alignment.CenterEnd), // Pesan dikirim berada di kanan
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.text ?: "",
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            )
            Text(
                text = formatTimestamp(item.timestamp ?: 0L),
                color = Color.Gray,
                fontSize = 10.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(top = 5.dp, end = 5.dp)
            )
        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ReceiveChatItem(item: MessageModel) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Batasi lebar maksimal pesan
                .align(Alignment.CenterStart), // Pesan diterima berada di kiri
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.text ?: "",
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(color = Color.Gray, shape = RoundedCornerShape(10.dp))
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            )
            Text(
                text = formatTimestamp(item.timestamp ?: 0L),
                color = Color.Gray,
                fontSize = 10.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
            )
        }
    }
}




@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ChatScreenPreview() {
    CooklyTheme {
        ChatScreen(
            navController = rememberNavController(),
            mainScreenEvent = remember { mutableStateOf(MainScreenEvent()) },
            action = {},
            chatId = TODO()
        )
    }
}