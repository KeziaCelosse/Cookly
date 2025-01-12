package com.example.cookly.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(1) } // Melacak tombol yang dipilih

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
        VideoScreenContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun VideoScreenContent(
    modifier: Modifier, navController: NavHostController
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick =  { navController.popBackStack() } ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "kembali",
                tint = Color(0xffD44000),
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "logo ini",
            modifier = Modifier.padding(end = 50.dp).size(95.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 80.dp, bottom = 70.dp)
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = "VIDEO",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(8.dp), // Bentuk elemen (opsional)
                    clip = false // Jika true, elemen akan terpotong mengikuti bentuk shadow
                )
                .background(Color.White)
                .border(
                    width = 0.5.dp,
                    color = Color(0xff864000),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            YouTubeVideo(
                youtubeVideoId = "8ZjtrOktGr0",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            Row(
                modifier =  Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Fried Rice",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff864000)

                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Food",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Asian",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Red onions, rice, chicken, eggs ...",
                fontSize = 14.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(8.dp), // Bentuk elemen (opsional)
                    clip = false // Jika true, elemen akan terpotong mengikuti bentuk shadow
                )
                .background(Color.White)
                .border(
                    width = 0.5.dp, // Ketebalan border
                    color = Color(0xff864000), // Warna border
                    shape = RoundedCornerShape(8.dp) // Opsional: Bentuk sudut (bisa disesuaikan)
                )
        ) {
            YouTubeVideo(
                youtubeVideoId = "cE6XD_DywFs",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            Row(
                modifier =  Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Spanish Garlic Chicken",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff864000)

                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Food",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(60.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "European",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Chicken pieces, garlic cloves, olive...",
                fontSize = 14.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(8.dp), // Bentuk elemen (opsional)
                    clip = false // Jika true, elemen akan terpotong mengikuti bentuk shadow
                )
                .background(Color.White)
                .border(
                    width = 0.5.dp, // Ketebalan border
                    color = Color(0xff864000), // Warna border
                    shape = RoundedCornerShape(8.dp) // Opsional: Bentuk sudut (bisa disesuaikan)
                )
        ) {
            YouTubeVideo(
                youtubeVideoId = "iBfMtwS6pVQ",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            Row(
                modifier =  Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "American CheeseCake",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff864000)

                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Food",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(60.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "American",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Cream cheese, granulated sugar...",
                fontSize = 14.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 8.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(8.dp), // Bentuk elemen (opsional)
                    clip = false // Jika true, elemen akan terpotong mengikuti bentuk shadow
                )
                .background(Color.White)
                .border(
                    width = 0.5.dp, // Ketebalan border
                    color = Color(0xff864000), // Warna border
                    shape = RoundedCornerShape(8.dp) // Opsional: Bentuk sudut (bisa disesuaikan)
                )
        ) {
            YouTubeVideo(
                youtubeVideoId = "aQD0ndQGpG0",
                lifecycleOwner = LocalLifecycleOwner.current
            )
            Row(
                modifier =  Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Authentic Falafel",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff864000)

                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Food",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(95.dp)
                        .height(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Middle Eastern",
                        fontSize = 12.sp,
                        color = Color(0xffFFEFCF),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Garlic, onion, parsley, cilantro...",
                fontSize = 14.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(horizontal = 10.dp).padding(bottom = 10.dp)
            )
        }
    }

}

@Composable
fun YouTubeVideo(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
){
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(175.dp)
            .clip(RoundedCornerShape(10.dp)),
        factory = {
            YouTubePlayerView(context = it).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady (youTubePlayer: YouTubePlayer){
                        youTubePlayer.loadVideo(youtubeVideoId,0f)
                    }
                })
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun VideoScreenPreview() {
    CooklyTheme {
        VideoScreen(rememberNavController())
    }
}