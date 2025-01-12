package com.example.cookly.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme

val items = listOf(
    Pair(R.drawable.mojito, "Mojito"),
    Pair(R.drawable.teriyaki, "Teriyaki"),
    Pair(R.drawable.sapi, "Beef"),
    Pair(R.drawable.kentang, "Potato"),
    Pair(R.drawable.eskrim, "Ice Cream")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(4) } // Melacak tombol yang dipilih

    Scaffold(
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(70.dp),
                containerColor = Color(0xffD44000),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Daftar tombol
                        val items = listOf(
                            Pair("Home", R.drawable.home),
                            Pair("Video", R.drawable.outline_video_library_24),
                            Pair("Add", Icons.Filled.Add),
                            Pair("Recipe", R.drawable.book),
                            Pair("Profile", R.drawable.baseline_person_outline_24)
                        )

                        items.forEachIndexed { index, item ->
                            val isSelected = selectedIndex == index
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    selectedIndex = index
                                    when (index) {
                                        0 -> navController.navigate(Screen.Homepage.route)
                                        1 -> navController.navigate(Screen.VideoScreen.route)
                                        2 -> navController.navigate(Screen.FormBaru.route)
                                        3 -> navController.navigate(Screen.Recipe.route)
                                        4 -> navController.navigate(Screen.Profile.route)
                                    }
                                }
                            ) {
                                // Ikon dengan background berubah
                                Box(
                                    modifier = Modifier
                                        .size(40.dp) // Ukuran background ikon
                                        .background(
                                            color = if (isSelected) Color(0xffFFEFCF) else Color.Transparent,
                                            shape = CircleShape // Background berbentuk lingkaran
                                        ),
                                    contentAlignment = Alignment.Center // Ikon berada di tengah background
                                ) {
                                    if (item.second is Int) {
                                        // Jika menggunakan resource gambar
                                        Image(
                                            painter = painterResource(id = item.second as Int),
                                            contentDescription = item.first,
                                            colorFilter = ColorFilter.tint(
                                                if (isSelected) Color(0xff864000) else Color(0xffFFEFCF)
                                            ), // Warna ikon berubah berdasarkan status
                                            modifier = Modifier.size(30.dp)
                                        )
                                    } else {
                                        // Jika menggunakan ikon material
                                        Icon(
                                            item.second as ImageVector,
                                            contentDescription = item.first,
                                            tint = if (isSelected) Color(0xff864000) else Color(0xffFFEFCF), // Warna ikon
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }

                                // Teks tetap konsisten
                                Text(
                                    text = item.first,
                                    color = Color(0xffFFEFCF), // Warna teks tidak berubah
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(top = 4.dp) // Spasi antara ikon dan teks
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        ProfileContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun ProfileContent(modifier: Modifier, navController: NavHostController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffD44000))
            .height(250.dp),
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick =  { navController.popBackStack() } ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "kembali",
                tint = Color(0xffffefcf),
                modifier = Modifier.align(Alignment.Start)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.kembaranku),
            contentDescription = "Foto Profile",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp).clip(CircleShape)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(id = R.string.kembaran),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xffffefcf),
            modifier = Modifier.padding(top = 15.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "802K",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffFFEFCF)
                )
                Text(
                    text = "Followers",
                    fontSize = 14.sp,
                    color = Color(0xffFFEFCF)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "3",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffFFEFCF)
                )
                Text(
                    text = "Following",
                    fontSize = 14.sp,
                    color = Color(0xffFFEFCF)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "900",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffFFEFCF)
                )
                Text(
                    text = "Favorite",
                    fontSize = 14.sp,
                    color = Color(0xffFFEFCF)
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp).padding(top = 220.dp)
        ) {
            Text(
                text = stringResource(id = R.string.myakun),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color(0xff864000),
                modifier = Modifier.padding(top = 15.dp)
            )
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .border(BorderStroke(1.dp, Color(0xff864000)), shape = RoundedCornerShape(10.dp))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xffF6F4F5))
                        .padding(vertical = 10.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_outline_24),
                            contentDescription = "Biodata",
                            tint = Color(0xff864000),
                            modifier = Modifier.padding(start = 13.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.kembaran),
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                }
                Divider(color = Color(0xff864000))
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xffF6F4F5))
                        .padding(vertical = 10.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = "Biodata",
                            tint = Color(0xff864000),
                            modifier = Modifier.padding(start = 13.dp)
                        )
                        Text(
                            text = "tanteono@gmail.com",
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                }
                Divider(color = Color(0xff864000))
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xffF6F4F5))
                        .padding(vertical = 10.dp)
                ) {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_lock_outline_24),
                            contentDescription = "Biodata",
                            tint = Color(0xff864000),
                            modifier = Modifier.padding(start = 13.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.paswod),
                            modifier = Modifier.padding(start = 13.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.padding(horizontal = 5.dp).padding(top = 30.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.collection),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xff864000),
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .padding(top = 10.dp)
        ) {
            items(items.size) { index ->
                val item = items[index]
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            BorderStroke(1.dp, Color(0xff864000)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .size(120.dp)
                        .background(Color(0xffF6F4F5)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = item.first),
                        contentDescription = "Item Koleksi ${index + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = item.second,
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color(0xff864000)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ProfileScreenPreview(){
    CooklyTheme{
        ProfileScreen(rememberNavController())
    }
}