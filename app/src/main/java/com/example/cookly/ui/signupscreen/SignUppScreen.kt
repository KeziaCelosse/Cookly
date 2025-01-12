package com.example.cookly.ui.signupscreen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.theme.customBackgroundColor
import com.example.cookly.ui.theme.customContentColor
import com.example.cookly.ui.theme.lightBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUppScreen(
    navController: NavController = rememberNavController(), // Default value untuk preview
    viewModel: SignUpViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    var emailEror by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordEror by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.signUpState.collectAsState(initial = null)
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        IconButton(onClick = {navController.popBackStack()}) {
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WELCOME TO COOKLY",
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                color = Color(0xFF864000),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = "Please Register in a few easy steps",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = Color(0xFF864000),
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
//                placeholder = { Text(text = "Email") },
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
                shape = RoundedCornerShape(25.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(14.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
//                placeholder = { Text(text = "Password") },
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
                shape = RoundedCornerShape(25.dp),
                singleLine = true,
                visualTransformation =
                if(passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
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
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = {
                    scope.launch {
                        viewModel.registerUser(email, password)
                    }
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =  Color(0xFFD44000),
                    contentColor = Color(0xffFFEFCF)
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(7.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (state?.isLoading == true) {
                CircularProgressIndicator()
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Already Have an account? Sign In",
                modifier = Modifier
                    .padding(15.dp)
                    .clickable {
                        navController.navigate(Screen.SignInScreen.route) {
                            popUpTo(Screen.SignUppScreen.route) { inclusive = true }
                        }
                    },
                fontWeight = FontWeight.Bold,
                color = Color(0xFF864000)
            )
        }

        // Handle success and error states
        LaunchedEffect(state?.isSuccess) {
            if (!state?.isSuccess.isNullOrEmpty()) {
                Toast.makeText(context, state?.isSuccess, Toast.LENGTH_LONG).show()
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.SignUppScreen.route) { inclusive = true }
                }
            }
        }
        LaunchedEffect(state?.isError) {
            if (!state?.isError.isNullOrEmpty()) {
                Toast.makeText(context, state?.isError, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SignUppScreenPreview() {
    CooklyTheme {
        SignUppScreen() // Default NavController untuk preview
    }
}
