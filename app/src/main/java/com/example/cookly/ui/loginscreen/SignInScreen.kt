package com.example.cookly.ui.loginscreen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.screen.RegularFont
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.theme.customBackgroundColor
import com.example.cookly.ui.theme.customContentColor
import com.example.cookly.ui.theme.lightBlue
import kotlinx.coroutines.launch
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var emailEror by rememberSaveable { mutableStateOf(false) }
    var passwordEror by rememberSaveable { mutableStateOf(false) }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)


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
                                .padding(start = 95.dp)
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
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 35.dp).padding(top = 65.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login),
                contentDescription = "Ini gambar makanan ya",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .padding(top = 8.dp)
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

//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(start = 30.dp, end = 30.dp),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
////                Text(
////                    text = "Enter your credential's to register",
////                    fontWeight = FontWeight.Medium,
////                    fontSize = 15.sp,
////                    color = Color.Gray,
////                    fontFamily = RegularFont
////                )
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.mail),
                                modifier = Modifier.padding(start = 16.dp),
                                fontSize = 13.sp,
                                color = Color(0xff864000)
                            )
                        },
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                            .height(63.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000),
                            unfocusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000)
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_email_24),
                                contentDescription = null,
                                tint = Color(0xff864000),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            )
                        },
                        shape = RoundedCornerShape(25.dp),
                        singleLine = true
//                    placeholder = {
//                        Text(text = "Email")
//                    }
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.pw),
                                modifier = Modifier.padding(start = 16.dp),
                                fontSize = 14.sp,
                                color = Color(0xff864000)
                            )
                        },
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth()
                            .height(63.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = if (passwordEror) Color.Red else Color(0xFF864000),
                            unfocusedBorderColor = if (passwordEror) Color.Red else Color(0xFF864000)
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_lock_24),
                                contentDescription = null,
                                tint = Color(0xff864000),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                            )
                        },
                        shape = RoundedCornerShape(25.dp),
                        singleLine = true,
                        visualTransformation =
                        if (passwordVisibility) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
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
//                    placeholder = {
//                        Text(text = "Password")
//                    }
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.loginUser(email, password, navController)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD44000),
                            contentColor = Color(0xffFFEFCF)
                        ),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text(
                            text = "Log In",
                            color = Color(0xffFFEFCF),
                            modifier = Modifier.padding(7.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (state.value?.isLoading == true) {
                            CircularProgressIndicator()
                        }
                    }
                    val annotatedText = buildAnnotatedString {
                        pushStringAnnotation(
                            tag = "SIGNUP",
                            annotation = Screen.SignUppScreen.route
                        )
                        append("New User? Sign Up")
                        pop()
                    }
                    ClickableText(
                        text = annotatedText,
                        modifier = Modifier.padding(15.dp),
                        style = TextStyle(
                            color = Color(0xFF864000),
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        ),
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(
                                tag = "SIGNUP",
                                start = offset,
                                end = offset
                            )
                                .firstOrNull()?.let { annotation ->
                                    navController.navigate(annotation.item)
                                }
                        }
                    )
                    Text(
                        text = "or connect with",
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "Google Icon",
                                modifier = Modifier.size(50.dp),
                                tint = Color.Unspecified
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        IconButton(onClick = {

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_facebook),
                                contentDescription = "Facebook Icon",
                                modifier = Modifier.size(50.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                    LaunchedEffect(key1 = state.value?.isSuccess) {
                        scope.launch {
                            if (state.value?.isSuccess?.isNotEmpty() == true) {
                                val success = state.value?.isSuccess
                                Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    LaunchedEffect(key1 = state.value?.isError) {
                        scope.launch {
                            if (state.value?.isError?.isNotEmpty() == true) {
                                val error = state.value?.isError
                                Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SignInScreenPreview() {
    CooklyTheme {
        SignInScreen(rememberNavController())
    }
}