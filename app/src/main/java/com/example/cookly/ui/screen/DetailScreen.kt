package com.example.cookly.ui.screen

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.cookly.R
import com.example.cookly.database.FoodDb
import com.example.cookly.navigation.Screen
import com.example.cookly.ui.theme.CooklyTheme
import com.example.cookly.ui.util.ViewModelFactory

const val KEY_ID_PRODUK = "idProduk"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = FoodDb.getInstance(context)
    val factory = ViewModelFactory(db.dao())
    val viewModel: DetailViewModel = viewModel(factory = factory)

    // State untuk form
    var nama by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var steps by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Food") }
    var selectedCategory by remember { mutableStateOf("Asian") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher untuk memilih gambar
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // Jika ID tidak null, maka load data dari database
    LaunchedEffect(id) {
        if (id != null) {
            val food = viewModel.getFood(id)
            food?.let {
                imageUri = Uri.parse(it.gambar)
                nama = it.nama
                ingredients = it.ingredients
                steps = it.steps
                selectedType = it.type
                selectedCategory = it.category
            }
        }
    }

    // Fungsi untuk validasi input
    fun isValidInput(): Boolean {
        return when {
            imageUri == null -> {
                Toast.makeText(context, "Please upload an image", Toast.LENGTH_LONG).show()
                false
            }
            nama.isBlank() -> {
                Toast.makeText(context, "Please enter a name", Toast.LENGTH_LONG).show()
                false
            }
            ingredients.isBlank() -> {
                Toast.makeText(context, "Please enter ingredients", Toast.LENGTH_LONG).show()
                false
            }
            steps.isBlank() -> {
                Toast.makeText(context, "Please enter steps", Toast.LENGTH_LONG).show()
                false
            }
            selectedCategory.isBlank() -> {
                Toast.makeText(context, "Please select a category", Toast.LENGTH_LONG).show()
                false
            }
            selectedType.isBlank() -> {
                Toast.makeText(context, "Please select a type", Toast.LENGTH_LONG).show()
                false
            }
            else -> true
        }
    }

    // Scaffold dengan TopAppBar
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logatas),
                        contentDescription = "Logo",
                        modifier = Modifier.size(120.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFFFEFCF)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isValidInput()) {
                            if (id == null) {
                                viewModel.insert(
                                    gambar = imageUri.toString(),
                                    nama = nama,
                                    selectedType = selectedType,
                                    selectedCategory = selectedCategory,
                                    ingredients = ingredients,
                                    steps = steps
                                )
                            } else {
                                viewModel.update(
                                    id = id,
                                    gambar = imageUri.toString(),
                                    nama = nama,
                                    selectedType = selectedType,
                                    selectedCategory = selectedCategory,
                                    ingredients = ingredients,
                                    steps = steps
                                )
                            }
                            navController.navigate(Screen.Recipe.route)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Save",
                            tint = Color(0xFFFFEFCF)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFD44000)
                )
            )
        }
    ) { padding ->
        AddFoodForm(
            modifier = Modifier.padding(padding),
            nama = nama,
            onNamaChange = { nama = it },
            ingredients = ingredients,
            onIngredientsChange = { ingredients = it },
            steps = steps,
            onStepsChange = { steps = it },
            selectedType = selectedType,
            onSelectedType = { selectedType = it },
            onSelectedCategory = { selectedCategory = it },
            imageUri = imageUri,
            onImageUriChange = { imageUri = it },
            launcher = launcher
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodForm(
    modifier: Modifier,
    nama: String,
    onNamaChange: (String) -> Unit,
    ingredients: String,
    onIngredientsChange: (String) -> Unit,
    steps: String,
    onStepsChange: (String) -> Unit,
    selectedType: String,
    onSelectedType: (String) -> Unit,
    onSelectedCategory: (String) -> Unit,
    imageUri: Uri?,
    onImageUriChange: (Uri?) -> Unit,
    launcher: ActivityResultLauncher<String>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text("Add Recipe", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xff864000))

        // Upload Image Button
        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            border = BorderStroke(width = 1.dp, color = Color(0xFFD44000)),
            shape = RoundedCornerShape(size = 40.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Uploaded Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Text("Upload Image", color = Color(0xff864000))
                }
            }
        }

        // Form Inputs
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            label = { Text("Food Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD44000),
                unfocusedBorderColor = Color(0xFFD44000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = ingredients,
            onValueChange = onIngredientsChange,
            label = { Text("Ingredients") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD44000),
                unfocusedBorderColor = Color(0xFFD44000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = steps,
            onValueChange = onStepsChange,
            label = { Text("Steps") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFD44000),
                unfocusedBorderColor = Color(0xFFD44000)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Radio Buttons for Type
        Card(
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(1.dp, Color(0xFFD44000)),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors( // Atur warna latar belakang
                containerColor = Color.Transparent // Warna dalam Card
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    RadioButton(
                        selected = selectedType == "Food",
                        onClick = { onSelectedType("Food") }
                    )
                    Text("Food")
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 50.dp)
                ) {
                    RadioButton(
                        selected = selectedType == "Drink",
                        onClick = { onSelectedType("Drink") }
                    )
                    Text("Drink")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    CooklyTheme {
        DetailScreen(rememberNavController())
    }
}
