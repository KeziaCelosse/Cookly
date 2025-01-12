package com.example.cookly.ui.onboarding


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.model.UserModel
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.util.HeightSpacer
import com.example.cookly.ui.util.MobileNumberTextField
import com.example.cookly.ui.util.ThemeSolidButton
import com.example.cookly.ui.util.WidthSpacer

@Composable
fun LogScreen(navController: NavHostController, action: (OnboardingEvents) -> Unit) {

    var userModel by remember {
        mutableStateOf(UserModel())
    }


    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        constraints

        Image(
            painter = painterResource(id = R.mipmap.globe_img),
            contentDescription = "Globe Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.5f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = Color.Gray.copy(alpha = 0.0f)),
            verticalArrangement = Arrangement.Bottom
        ) {

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chat_u_logo),
                    contentDescription = "chat u logo",
                    modifier = Modifier
                        .width(150.dp)
                        .offset(x = -(20.dp))
                )
                HeightSpacer()
                Text(
                    text = "Ready to Chat?",
                    color = Color.White,
                    fontSize = 24.sp
                )
                HeightSpacer()
                Text(
                    text = "Sign in to pick up right where you left off.",
                    color = Color.White,
                    fontSize = 12.sp
                )
                HeightSpacer(height = 60.dp)

                MobileNumberTextField(
                    label = "Mobile Number",
                    value = userModel.mobileNumber ?: "" ) {

                    if (it.isDigitsOnly() && it.length <= 10){
                        userModel = userModel.copy(
                            mobileNumber = it
                        )
                    }
                }

                HeightSpacer(height = 20.dp)

                Text(
                    text = "Create an account!",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                HeightSpacer(height = 20.dp)

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    WidthSpacer(width = 20.dp)
                    ThemeSolidButton(
                        text = "Send Otp",
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.6f)
//                            .weight(0.5f)
                    ) {
                        if (userModel.mobileNumber?.length == 10) {
                            action(OnboardingEvents.LoginUpClick(userModel) { status ->
                                if (status) {
                                    navController.navigate(OnboardingNavigationObject.OTP_SCREEN)
                                }
                            })
                        }
                    }
                }
                HeightSpacer(height = 20.dp)
            }
        }

    }


}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LogScreenPreview() {
    CooklyTheme {
        LogScreen(
            navController = rememberNavController(),
            action = {},
        )
    }
}