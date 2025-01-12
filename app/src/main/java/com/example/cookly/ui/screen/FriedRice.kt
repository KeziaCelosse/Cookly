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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.annotation.DrawableRes
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.shadow
import com.example.cookly.R
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class Recipeee(
    val title: String,
    val category: String,
    val cookingTime: String,
    val energy: String,
    val rating: String,
    val description: String,
    val reviews: String,
    val ingredients: List<Ingredient>
)

data class Ingredienttt(@DrawableRes val image: Int, val title: String, val subtitle: String)

val FriedRice = Recipeee(
    title = "Strawberry Cake",
    category = "Desserts",
    cookingTime = "50 min",
    energy = "620 kcal",
    rating = "4,9",
    description = "This dessert is very tasty and not difficult to prepare. Also, you can replace strawberries with any other berry you like.",
    reviews = "84 photos     430 comments",
    ingredients = listOf(
        Ingredient(R.drawable.rice, "Rice", "1 bowl"),
        Ingredient(R.drawable.chilli, "Red Chili", "10 g"),
        Ingredient(R.drawable.shallots, "Shallots", "50 g"),
        Ingredient(R.drawable.kecapmanis, "Sweet soy sauce", "20 g"),
        Ingredient(R.drawable.salt, "Salt", "3 g"),
        Ingredient(R.drawable.mustardgreen, "Mustard greens", "50 g"),
        Ingredient(R.drawable.garlic, "Garlic", "10 g"),
        Ingredient(R.drawable.oil, "Cooking oil", "30 g"),
        Ingredient(R.drawable.mushroom, "Mushroom broth", "5 g"),
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriedRice(navController: NavHostController) {
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
        FriedRiceContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun FriedRiceContent(
    padding: Modifier,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp)
            .background(Color(0xffD44000))
            .height(250.dp)
            .padding(horizontal = 25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        YouTubePlayer(
            youtubeVideoId = "8ZjtrOktGr0",
            lifecycleOwner = LocalLifecycleOwner.current
        )
    }

    Spacer(modifier = Modifier.height(26.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp)
            .padding(top = 325.dp, bottom = 5.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "FRIED RICE",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Resep by",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.dapurnajah),
                contentDescription = "Author Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                        text = "Dapur Najah",
                    fontSize = 15.sp,
                    color = Color(0xff864000),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.MainCourse.route)
                    }
                )
                Text(
                    text = "Cook",
                    fontSize = 13.sp,
                    color = Color(0xff864000),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.MainCourse.route)
                    }
                )
            }
            Spacer(modifier = Modifier.width(130.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_chat_24),
                contentDescription = "Ini Splash Screen",
                modifier = Modifier.size(30.dp).clickable { navController.navigate(Screen.MainChatScreen.route) }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Nasi goreng is a typical Indonesian dish in the form of rice fried with spices" +
                    "such as soy sauce, onions, and spices, often served " +
                    "with eggs or chicken pieces.",
            fontSize = 14.sp,
            color = Color(0xff864000),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color(0xff864000)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_access_time_24),
                    contentDescription = "kembaran",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(50.dp)
                ) {
                    Text(
                        text = "Cooking Time",
                        fontSize = 15.sp,
                        color = Color(0xff864000),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.MainCourse.route)
                        }
                    )
                    Text(
                        text = "35 min",
                        fontSize = 13.sp,
                        color = Color(0xff864000),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.MainCourse.route)
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color(0xff864000)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 28.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.cook),
                    contentDescription = "kembaran",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.height(50.dp)
                ) {
                    Text(
                        text = "Cuisine",
                        fontSize = 15.sp,
                        color = Color(0xff864000),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.MainCourse.route)
                        }
                    )
                    Text(
                        text = "Asian",
                        fontSize = 13.sp,
                        color = Color(0xff864000),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.MainCourse.route)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text= "Ingredients",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        Spacer(modifier = Modifier.height(10.dp))
        IngredientsListtt(recipe = FriedRice)
        Text(
            text= "Calculator",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .background(
                    color = Color(0xFFD44000),
                    shape = RoundedCornerShape(10.dp) // Memberikan bentuk melengkung pada background
                )
                .border(
                    BorderStroke(2.dp, Color(0xFF864000)),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 85.dp, vertical = 5.dp)
                .clickable { // Menambahkan aksi klik
                    navController.navigate(Screen.MassConversionCalculator.route) // Navigasi ke screen target
                },
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(R.drawable.kalkulator),
                contentDescription = "kembaran",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp)) // Spacer horizontal untuk jarak
            Text(
                text = "Calculate to gr",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFEFCF)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text= "Steps to make fried rice",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        val step = listOf(
            "Grind the shallots, garlic, red chili, bird's eye chili, and salt into a smooth paste.",
            "Heat a little oil in a pan, then sauté the spice paste until fragrant and cooked.",
            "Add the chopped mustard greens (sawi) to the pan, then stir-fry until wilted.",
            "Add the rice to the pan, then mix it evenly with the spices and mustard greens",
            "Add mushroom broth powder and sweet soy sauce to taste, then stir until the rice is evenly mixed and changes color.",
            "Taste the fried rice to ensure the seasoning is balanced. Add more seasoning if needed",
            "Remove the fried rice from the pan and serve it hot.",
        )
        step.forEach { step ->
            Text(
                text = "• $step", // Bullet point
                fontSize = 14.sp,
                color = Color(0xff864000),
                modifier = Modifier.padding(bottom = 2.dp) // Spasi antar item
            )
        }
    }

}

// IngredientsList Function
@Composable
fun IngredientsListtt(recipe: Recipeee) {
    EasyGriddd(nColumns = 3, items = recipe.ingredients) { ingredient ->
        IngredientCarddd(
            iconResource = ingredient.image,
            title = ingredient.title,
            subtitle = ingredient.subtitle,
            modifier = Modifier.padding(8.dp)
        )
    }
}

// EasyGrid Function
@Composable
fun <T> EasyGriddd(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
    Column(Modifier.padding(5.dp)) {
        for (i in items.indices step nColumns) {
            Row {
                for (j in 0 until nColumns) {
                    if (i + j < items.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
    }
}

// IngredientCard Function
@Composable
fun IngredientCarddd(
    @DrawableRes iconResource: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F4F5)),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .shadow(
                    elevation = 12.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(12.dp), // Bentuk elemen (opsional)
                    clip = false // Jika true, elemen akan terpotong mengikuti bentuk shadow
                )
                .border(
                    width = 0.5.dp, // Ketebalan border
                    color = Color(0xff864000), // Warna border
                    shape = RoundedCornerShape(12.dp) // Opsional: Bentuk sudut (bisa disesuaikan)
                )
        ) {
            Box(
                contentAlignment = Alignment.Center, // Gambar berada di tengah kartu
                modifier = Modifier.fillMaxSize() // Isi seluruh ukuran kartu
            ) {
                Image(
                    painter = painterResource(id = iconResource),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .size(50.dp)

                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(100.dp),
            color = Color.Black
        )
        Text(
            text = subtitle,
            fontSize = 12.sp,
            modifier = Modifier.width(100.dp),
            color = Color.Gray
        )
    }
}



@Composable
fun YouTubePlayer(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
){
    AndroidView(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
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
fun FriedRicePreview() {
    CooklyTheme {
        FriedRice(rememberNavController())
    }
}