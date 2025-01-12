package com.example.cookly.ui.screen


import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.MainActivityNavigationNames
import com.example.cookly.R
import com.example.cookly.model.UserFirestore
import com.example.cookly.model.UserModel
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.event.MainScreenAction
import com.example.cookly.ui.event.MainScreenEvent
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.util.HeightSpacer
import com.example.cookly.ui.util.ImageCircle
import com.example.cookly.ui.util.WidthSpacer
import com.example.cookly.ui.viewmodel.ChatRoomViewModel
import com.example.cookly.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainChatScreen(
    navController: NavHostController,
    mainScreenEvent: State<MainScreenEvent>,
    action: (MainScreenAction) -> Unit
) {

    val viewModel: UserViewModel = viewModel()
    val viewModel2: ChatRoomViewModel = viewModel()
    val users = viewModel.users.collectAsState()
    val chatRooms = viewModel2.chatRooms.collectAsState()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) { constraints

        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                HeightSpacer()
                Text(
                    text = "Cookly-Chat",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color(0xffD44000)
                )

                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = Color(0xffd44000), shape = CircleShape)
                        .padding(5.dp)
                )
            }


            Text(
                text = "Chats",
                fontSize = 16.sp,
                color = Color(0xffD44000),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            HeightSpacer()
            LazyColumn {
                items(users.value.size) { user ->
                    if ( users.value[user].uid != FirebaseAuth.getInstance().currentUser!!.uid) {

                        ChatItem(
                            userItem = users.value[user],
                            onClick = {
                                val otherUserId = users.value[user].uid
                                if (otherUserId.isNotEmpty()) {
                                    viewModel2.createRoom(otherUserId, navController)
                                } else {
                                    Log.e("MainChatScreen", "Invalid userId: $otherUserId")
                                }
                            }
                        )

                    }
                }
            }

        }


        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(15.dp),
            containerColor = Color(0xffD44000),
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = ""
            )
        }

    }
}



@Composable
fun ChatItem(userItem: UserFirestore, onClick: () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
        ,
        verticalArrangement = Arrangement.Bottom
    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {

                ImageCircle()

                WidthSpacer(width = 20.dp)

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
//                modifier = Modifier.fillMaxSize()
                    modifier = Modifier
                        .height(80.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = userItem.username ?: "",
                        fontSize = 20.sp,
                        color = Color(0xff864000),
                        fontWeight = FontWeight.Bold
                    )
                    HeightSpacer(height = 10.dp)
                    Text(
                        text = "Hey, What's Up?",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
            Column (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .height(80.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "4:29 pm",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        }


        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xff864000)
        )
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainChatScreenPreview() {
    CooklyTheme {
        MainChatScreen(
            navController = rememberNavController(),
            mainScreenEvent = remember { mutableStateOf(MainScreenEvent()) },
            action = {}
        )
    }
}