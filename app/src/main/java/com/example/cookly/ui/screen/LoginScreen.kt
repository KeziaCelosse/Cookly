package com.example.cookly.ui.screen

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.auth.Authentication
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back",
                                tint = Color(0xFFB11116),

                                )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo",
                            modifier = Modifier
                                .padding(start = 105.dp)
                                .size(100.dp)
                        )
                    }
                },
                title = {},
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }

    ) { padding ->
        LoginContent(Modifier.padding(padding), navController, LocalContext.current)
    }
}

@Composable
fun LoginContent(modifier: Modifier, navController: NavHostController, context: Context) {
    var username by rememberSaveable { mutableStateOf("") }
    var usernameEror by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordEror by rememberSaveable { mutableStateOf(false) }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }


    Column (
        modifier = modifier.fillMaxSize().padding(horizontal = 35.dp).padding(top = 45.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Ini gambar makanan ya",
            contentScale = ContentScale.Crop,
            modifier= Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(30.dp))

        )
        Column(
            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.login),
                style = TextStyle(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff864000)
                ),
                modifier = Modifier
                    .padding(top = 9.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            OutlinedTextField(
                value = username,
                onValueChange = {username = it},
                label = {
                    Text(
                        text = stringResource(id = R.string.name),
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_person_outline_24),
                        contentDescription = null,
                        tint = Color(0xff864000),
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )
                },
//                isError = usernameEror,
//                trailingIcon = { IconPickerLogin(usernameEror, "") },
//                supportingText = { ErrorHintLogin(usernameEror)},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .height(63.dp)

            )
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = {
                    Text(
                        text = stringResource(id = R.string.pw),
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.outline_lock_24),
                        contentDescription = null,
                        tint = Color(0xff864000),
                        modifier = Modifier
                            .padding(start = 20.dp)
                    )
                },
//                isError = passwordEror,
                trailingIcon = {
                    IconButton(onClick = {passwordVisibility = !passwordVisibility}) {
                        Icon(
                            painter =
                            if (passwordVisibility)
                                painterResource(id = R.drawable.baseline_visibility_24)
                            else painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = null,
                            tint = Color(0xFF864000)
                        )
                    }
                },
                visualTransformation =
                if(passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
//                supportingText = { ErrorHintLogin(passwordEror)},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .height(63.dp)

            )
//            Text(
//                text = stringResource(R.string.forgot),
//                style = TextStyle(
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.Normal,
//                    color = Color(0xff864000),
//                ),
//                textAlign = TextAlign.End,
//                modifier = Modifier.fillMaxWidth().padding(top = 30.dp)
//            )

            Button(
                onClick = {
                    navController.navigate(Screen.Homepage.route)
//                    usernameEror=(username.isBlank() || username == "0")
//                    passwordEror=(password.isBlank() || password == "0")
//
//                    if (!usernameEror && !passwordEror) {
//                        Authentication.login(username, password, navController, context)
//                    }
                },
                modifier= Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000))

            ) {
                Text(
                    text = stringResource(id = R.string.log),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xffffefcf)
                )
            }
            Row(
                modifier = Modifier.padding(top = 30.dp),

                ) {
                Text(
                    text = stringResource(R.string.dont),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xff864000),
                    ),
                )
                ClickableText(
                    text = AnnotatedString(" Register "),
                    onClick = {navController.navigate(Screen.Register.route)},
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff864000),
                    ),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun IconPickerLogin (isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHintLogin(isError: Boolean) {
    if (isError){
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    CooklyTheme {
        LoginScreen(rememberNavController())
    }
}