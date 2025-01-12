package com.example.cookly.ui.screen

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun RegistrationScreen(navController: NavHostController) {

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
    ){padding ->
        RegistrationContent(Modifier.padding(padding), navController, LocalContext.current)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationContent(modifier: Modifier, navController: NavHostController, context: Context) {


    var username by rememberSaveable { mutableStateOf("") }
    var usernameError by rememberSaveable { mutableStateOf(false) }

    var email by rememberSaveable { mutableStateOf("") }
    var emailEror by rememberSaveable { mutableStateOf(false) }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf(false) }

    var konfirPassword by rememberSaveable { mutableStateOf("") }
    var konfirPasswordError by rememberSaveable { mutableStateOf(false) }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            color = Color(0xFF864000),
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.descregis),
            color = Color(0xFF864000),
            style = TextStyle(fontSize = 15.sp),
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = stringResource(id = R.string.name),
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 16.dp),
                )
            },
//            isError = usernameError,
//            trailingIcon = { IconPicker(usernameError, "")},
//            supportingText = { ErrorHint(usernameError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next

            ),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000),
                unfocusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000)
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(63.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    text = stringResource(id = R.string.mail),
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 13.sp
                )
            },
//            isError = emailEror,
//            trailingIcon = { IconPicker(emailEror, "")},
//            supportingText = { ErrorHint(emailEror)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000),
                unfocusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000)
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(63.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(id = R.string.pw),
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 14.sp
                )
            },

            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000),
                unfocusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000)
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(63.dp),
//              isError = passwordError,
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
//            supportingText = { ErrorHint(passwordError)},
        )
        OutlinedTextField(
            value = konfirPassword,
            onValueChange = { konfirPassword = it },
            label = {
                Text(
                    text = stringResource(id = R.string.confirmpw),
                    modifier = Modifier.padding(start = 16.dp),
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(25.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000),
                unfocusedBorderColor = if (emailEror) Color.Red else Color(0xFF864000)
            ),
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(63.dp),
//            isError = konfirPasswordError,
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
//            supportingText = { ErrorHint(konfirPasswordError)},
        )
        Row (modifier = Modifier.padding(top = 43.dp)){
            Text(
                text = "Sudah punya akun?",
                style = TextStyle(
                    color = Color(0xFF864000),
                    fontSize = 14.sp)
            )
            ClickableText(
                text = AnnotatedString(" Login "),
                onClick = {navController.navigate(Screen.Login.route)},
                style = TextStyle(
                    color = Color(0xFF864000),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "sekarang",
                style = TextStyle(
                    color = Color(0xFF864000),
                    fontSize = 14.sp)
            )
        }
        Button(
            onClick = { navController.navigate(Screen.Login.route)
                usernameError = (username == "" || username == "0")
                emailEror = (email == "" || email == "0")
                passwordError = (password == "" || password == "0")
                konfirPasswordError = (konfirPassword == "" || konfirPassword == "0")
                if (usernameError || emailEror || passwordError || konfirPasswordError)
                    return@Button
//                Authentication.register(
//                    username,
//                    email,
//                    password,
//                    navController,
//                    context
//                )
            },
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000))
        )
        {
            Text(
                text = stringResource(id = R.string.regis),
                style = TextStyle(fontWeight = FontWeight.Bold),
                color = Color(0xffFFEFCF)
            )
        }
        Text(
            text = stringResource(id = R.string.descregis2),
            style = TextStyle(
                color = Color(0xFF864000),
                fontSize = 14.sp),
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
//        Image(imageVector = unit)
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError){
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    CooklyTheme {
        RegistrationScreen(rememberNavController())
    }
}