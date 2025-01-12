package com.example.cookly.ui.screen

import android.content.res.Configuration
import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cookly.R
import com.example.cookly.database.FoodDb
import com.example.cookly.model.Food
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen (navController: NavHostController, id : Long? = null) {
    var selectedIndex by remember { mutableIntStateOf(3) } // Melacak tombol yang dipilih

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
        RecipeContent(showList = true, Modifier.padding(padding), navController = navController)
    }
}

@Composable
fun RecipeContent(showList: Boolean, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val db = FoodDb.getInstance(context)
    val factory = ViewModelFactory(db.dao())
    val viewModel: DetailViewModel = viewModel(factory = factory)
    val foodList by viewModel.getAllFoods().collectAsState(initial = emptyList()) // Ambil data dari ViewModel

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffD44000))
            .height(70.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 10.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "kembali",
                    tint = Color(0xffffefcf),
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logatas),
                contentDescription = "Logo",
                modifier = Modifier.padding(start = 80.dp).size(120.dp)
            )
        }

    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 90.dp)
    ) {
        Text(
            text = "MY RECIPE",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (showList) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 4.dp)
            ) {
                items(foodList) { food ->
                    ListItem2(food = food) {
                        navController.navigate(Screen.FormEdit.withId(food.id))
                    }
                }
            }
        }
    }
}


@Composable
fun ListItem2(food: Food, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .border(BorderStroke(1.dp, Color(0xFFD44000)), RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        val imageUri = Uri.parse(food.gambar)
        Image(
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = null,
            modifier = Modifier
                .padding(13.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = food.nama,
                    maxLines = 1,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = food.ingredients,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
            Row(
                modifier = Modifier.padding(end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(35.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000))

                ) {
                    Text(
                        text = food.type,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete",
                    tint = Color(0xff864000),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RecipeScreenPreview() {
    CooklyTheme {
        RecipeScreen(rememberNavController())
    }
}