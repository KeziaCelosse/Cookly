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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

data class Recipee(
    val title: String,
    val category: String,
    val cookingTime: String,
    val energy: String,
    val rating: String,
    val description: String,
    val reviews: String,
    val ingredients: List<Ingredient>
)

data class Ingredientt(@DrawableRes val image: Int, val title: String, val subtitle: String)

val CheeseCake = Recipee(
    title = "Strawberry Cake",
    category = "Desserts",
    cookingTime = "50 min",
    energy = "620 kcal",
    rating = "4,9",
    description = "This dessert is very tasty and not difficult to prepare. Also, you can replace strawberries with any other berry you like.",
    reviews = "84 photos     430 comments",
    ingredients = listOf(
        Ingredient(R.drawable.creamcheese, "Cream Cheese", " 525 g"),
        Ingredient(R.drawable.biskuit, "Biscuit", "160 g"),
        Ingredient(R.drawable.telur, "Eggs", "3 pcs"),
        Ingredient(R.drawable.mentega, "Unsalted butter", "55 g"),
        Ingredient(R.drawable.maizena, "Cornstarch", "4 tbsp"),
        Ingredient(R.drawable.vanila, "Vanila Extract", "1/2 tsp"),
        Ingredient(R.drawable.whipcream, "Whipping Cream", "230 g"),
        Ingredient(R.drawable.airlemon, "Lemon Juice", "2 tbsp"),
    )
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheeseCake(navController: NavHostController) {
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
        CheeseCakeContent(Modifier.padding(padding), navController)
    }
}

@Composable
fun CheeseCakeContent(
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
        YouTubePlayerCheese(
            youtubeVideoId = "iBfMtwS6pVQ",
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
            text = "AMERICAN CHEESE CAKE",
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
                painter = painterResource(R.drawable.ho),
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
                    text = "Luvita Ho",
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
            Spacer(modifier = Modifier.width(154.dp))
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
            text = "American cheesecake is a rich and creamy dessert made with a buttery biscuit " +
                    "crust, a smooth cream cheese filling, and a hint of vanilla and lemon, baked" +
                    "to perfection and often topped with fresh fruit, whipped cream, or a sweet glaze.",
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
                        text = "1 hour",
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
                        text = "American",
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
        IngredientsListt(recipe = CheeseCake)
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
            text= "Steps to make Cheese Cake",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xff864000)
        )
        val step = listOf(

            "Mix crushed biscuits with melted butter. Press into a pan and chill in the fridge.",
            "Beat cream cheese and sugar until smooth. Add eggs one at a time, mixing well.",
            "Mix in cornstarch, whipping cream, lemon juice, and vanilla extract until well combined.",
            "Pour the filling over the crust. Bake at 160°C (320°F) for 50-60 minutes or until set.",
            "Let the cheesecake cool to room temperature, then refrigerate for at least 4 hours before serving.",
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
fun IngredientsListt(recipe: Recipee) {
    EasyGridd(nColumns = 3, items = recipe.ingredients) { ingredient ->
        IngredientCardd(
            iconResource = ingredient.image,
            title = ingredient.title,
            subtitle = ingredient.subtitle,
            modifier = Modifier.padding(8.dp)
        )
    }
}

// EasyGrid Function
@Composable
fun <T> EasyGridd(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
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
fun IngredientCardd(
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
fun YouTubePlayerCheese(
    youtubeVideoId: String,
    lifecycleOwner: LifecycleOwner
){
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
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
fun CheeseCakePreview() {
    CooklyTheme {
        CheeseCake(rememberNavController())
    }
}