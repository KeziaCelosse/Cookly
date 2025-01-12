package com.example.cookly.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCookScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Row (
                        modifier = Modifier.fillMaxWidth(), // Mengisi lebar TopAppBar
                        horizontalArrangement = Arrangement.Center, // Pusatkan konten secara horizontal
                        verticalAlignment = Alignment.CenterVertically // Sejajarkan secara vertikal
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = stringResource(id = R.string.desclogo),
                            modifier = Modifier
                                .padding(start = 16.dp)
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
        ScreenContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp, vertical = 45.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.mainsc),
            contentDescription = "Ini gambar landing page",
            modifier= Modifier
                .fillMaxWidth()
                .size(200.dp)
                .clip(RoundedCornerShape(25.dp))
        )
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = stringResource(id = R.string.welcome),
                color = Color(0xFF864000),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(top = 43.dp)
            )
            Text(
                text = stringResource(id = R.string.desc),
                color = Color(0xFF864000),
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier
                    .padding(top = 15.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = { navController.navigate(Screen.SignInScreen.route) },
                modifier = Modifier.padding(top = 43.dp).height(42.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000))
            )
            {
                Text(
                    text = stringResource(id = R.string.log),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Color(0xFFFFEFCF)

                )
            }
            Button(
                onClick = { navController.navigate(Screen.SignUppScreen.route) },
                modifier = Modifier.padding(top = 10.dp).height(42.dp).fillMaxWidth(),
                border = BorderStroke(width = 1.dp, color = Color(0xFF864000)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
            )
            {
                Text(
                    text = stringResource(id = R.string.regis),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Color(0xFFD44000)
                )
            }
            Text(
                text = stringResource(id = R.string.desc2),
                style = TextStyle(fontSize = 13.sp),
                modifier = Modifier.padding(top = 26.dp),
                color = Color(0xFF864000)
            )
        }

    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    CooklyTheme {
        MainCookScreen(rememberNavController())
    }
}
