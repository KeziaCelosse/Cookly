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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cookly.R
import com.example.cookly.model.Food
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme

val itemssB = listOf(
    Pair(R.drawable.mojito, "Mojito"),
    Pair(R.drawable.teriyaki, "Teriyaki"),
    Pair(R.drawable.sapi, "Beef"),
    Pair(R.drawable.kentang, "Potato"),
    Pair(R.drawable.eskrim, "Ice Cream")
)

@Composable
fun BeverageScreen(navController: NavHostController) {
    var selectedIndex by remember { mutableStateOf(0) }

//    val showList by remember { mutableStateOf(true) }

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
                                        0 -> navController.navigate(Screen.MainCookScreen.route)
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
        BeverageScreenContent(modifier = Modifier.padding(padding), navController = navController)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeverageScreenContent (
    modifier: Modifier,
    navController: NavHostController
){
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_food),
                    fontSize = 16.sp,
                    color = Color(0xFF865C32), // Warna teks lebih coklat untuk placeholder
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { /* Aksi pencarian */ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xffD44000))
                        .size(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color(0xFFF6F4F5) // Warna putih untuk ikon
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Search
            ),
            shape = RoundedCornerShape(10.dp), // Membulatkan sudut TextField
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(60.dp)
                .shadow(4.dp, RoundedCornerShape(10.dp)), // Memberikan shadow halus
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFF6F4F5), // Warna latar belakang TextField
                focusedIndicatorColor = Color.Transparent, // Menghapus garis border fokus
                unfocusedIndicatorColor = Color.Transparent // Menghapus garis border unfocus
            )
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "NEWEST FOOD",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )

        Spacer(modifier = Modifier.height(25.dp))

        LazyRow{
            items(itemssB.size) { index ->
                val item = itemssB[index]
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .border(
                            BorderStroke(1.dp, Color(0xff864000)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .width(100.dp)
                        .height(135.dp)
                        .background(Color(0xffF6F4F5)),
                    contentAlignment = Alignment.BottomCenter,
                ) {
                    Image(
                        painter = painterResource(id = item.first),
                        contentDescription = "Item Koleksi ${index + 1}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .fillMaxWidth()
                            .background(Color(0xffADADAD).copy(alpha = 0.70f)) // Background teks dengan transparansi
                            .padding(vertical = 2.dp) // Padding agar lebih rapi
                    ) {
                        Text(
                            text = item.second,
                            fontSize = 11.sp,
                            color = Color(0xff864000),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = "EXPLORE CUISINES",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Asian",
                fontSize = 13.sp,
                color = Color(0xff864000),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Homepage.route)
                }
            )

            Text(
                text = "European",
                fontSize = 13.sp,
                color = Color(0xff864000),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.MainCourse.route)
                }
            )

            Text(
                text = "American",
                fontSize = 13.sp,
                color = Color(0xff864000),
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Dessert.route)
                }
            )
            Button(
                onClick = {},
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .width(146.dp)
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000))

            ) {
                Text(
                    text = "Middle Eastern",
                    fontSize = 13.sp,
                    color = Color(0xffFFEFCF)
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))



        FoodItemB(
            imageRes = R.drawable.authenticfalafel,
            category = "Food",
            title = "Authentic Falafel",
            description = "Garlic, onion, parsley, cilantro...",
            navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        FoodItemB(
            imageRes = R.drawable.shishtawook,
            category = "Food",
            title = "Shishtawook",
            description = "Lemon juice, yogurt, olive oil...",
            navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        FoodItemB(
            imageRes = R.drawable.arayes,
            category = "Food",
            title = "Arayes",
            description = "Beef, onion, ground cumin, paprika...",
            navController = navController
        )

        Spacer(modifier = Modifier.height(20.dp))

        FoodItemB(
            imageRes = R.drawable.blazingbatataharra,
            category = "Food",
            title = "Blazing Batata Harra,",
            description = "Potatoes, garlic, fresh cilantro...",
            navController = navController
        )
        Spacer(modifier = Modifier.height(20.dp))

        FoodItemB(
            imageRes = R.drawable.chickenkafta,
            category = "Food",
            title = "Chicken Kafta",
            description = "Potatoes, fresh cilantro, red chili ...",
            navController = navController
        )
        Spacer(modifier = Modifier.height(20.dp))

        FoodItemB(
            imageRes = R.drawable.lebanesefattoushsalad,
            category = "Food",
            title = "Lebanese fattoush salad",
            description = "Tomatoes, cucumber, radishes, bell...",
            navController = navController
        )
    }
}

@Composable
fun FoodItemB(imageRes: Int, category: String, title: String, description: String, navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color(0xff864000)))
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .width(80.dp)
                    .height(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD44000)),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = category,
                    fontSize = 12.sp,
                    color = Color(0xffFFEFCF),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color(0xff864000),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun ListItemB(food: Food, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .border(BorderStroke(1.dp, Color(0xFFACACAC)), RoundedCornerShape(12.dp)),
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
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = food.nama,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = food.ingredients,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun BeverageScreenPreview() {
    CooklyTheme {
        BeverageScreen(rememberNavController())
    }
}



