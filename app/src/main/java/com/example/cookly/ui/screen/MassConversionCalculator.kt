package com.example.cookly.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cookly.R
import com.example.cookly.ui.theme.CooklyTheme

val buttonListt = listOf(
    "kg", "g", "lb", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "+",
    "1", "2", "3", "-",
    "AC", "0", ".", "="
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassConversionCalculator(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = Color(0xFFFFEFCF)
                        )
                    }
                },
                title = {
                    Text(
                        text = "Kalkulator Massa",
                        fontSize = 18.sp,
                        color = Color(0xffffefcf),
                        modifier = Modifier.padding(start = 70.dp)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFFD44000),
                    titleContentColor = Color.White
                )
            )
        },
        content = { padding ->
            MassConversionContent(Modifier.padding(padding), navController)
        }
    )
}

@Composable
fun MassConversionContent(
    padding: Modifier,
    navController: NavHostController
) {
    val equationText = remember { mutableStateOf("") }
    val resultText = remember { mutableStateOf("") }

    Box(modifier = padding.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            // Equation Text
            Text(
                text = equationText.value,
                style = TextStyle(fontSize = 30.sp, textAlign = TextAlign.End),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            Spacer(modifier = Modifier.weight(1f))

            // Result Text
            Text(
                text = resultText.value,
                style = TextStyle(fontSize = 60.sp, textAlign = TextAlign.End),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Buttons Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(buttonListt) { btn ->
                    CalculatorButton(btn = btn) {
                        handleButtonClick(btn, equationText, resultText)
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(btn: String, onClick: () -> Unit) {
    Box(modifier = Modifier.padding(15.dp)) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(100.dp, 70.dp), // Tombol berbentuk persegi panjang (80x50dp)
            shape = RoundedCornerShape(15.dp),
            containerColor = getColor(btn), // Pastikan btn memiliki nilai valid
            contentColor = Color.Black
        ) {
            Text(
                text = btn,
                fontSize = 22.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }
    }
}

fun getColor(btn: String): Color {
    return when {
        btn == "C" || btn == "AC" -> Color(0xFF8A7CA8)
        btn in listOf("/", "*", "+", "-", "=") -> Color(0xFF6A9C89)
        btn in listOf("kg", "g", "lb") -> Color(0xFFD44000) // Warna spesifik untuk unit
        else -> Color(0xFFFFF5E4)
    }
}

fun handleButtonClick(
    button: String,
    equationText: MutableState<String>,
    resultText: MutableState<String>
) {
    when (button) {
        "AC" -> {
            equationText.value = ""
            resultText.value = ""
        }
        "=" -> {
            try {
                // Kalkulasi hasil dari input dengan unit
                val result = calculateEquation(equationText.value)
                resultText.value = "${result?.toInt() ?: "Error"} gr"
            } catch (e: Exception) {
                resultText.value = "Error"
            }
        }
        else -> {
            equationText.value += button
        }
    }
}

fun calculateEquation(equation: String): Double? {
    // Regex untuk mendeteksi angka dan unit
    val regex = Regex("""(\d+(\.\d+)?)(kg|g|lb)?""")
    val matches = regex.findAll(equation)

    // List untuk menyimpan nilai dalam gram
    val values = mutableListOf<Double>()

    // Operator
    var operator: String? = null

    for (match in matches) {
        val value = match.groupValues[1].toDoubleOrNull() ?: continue
        val unit = match.groupValues[3]

        // Konversi nilai ke gram
        val valueInGrams = when (unit) {
            "kg" -> value * 1000
            "g" -> value
            "lb" -> value * 453.592
            else -> value // Jika tidak ada unit, dianggap gram
        }

        if (operator == null) {
            values.add(valueInGrams)
        } else {
            // Kalkulasi berdasarkan operator
            val previousValue = values.removeLast()
            values.add(
                when (operator) {
                    "+" -> previousValue + valueInGrams
                    "-" -> previousValue - valueInGrams
                    "*" -> previousValue * valueInGrams
                    "/" -> if (valueInGrams != 0.0) previousValue / valueInGrams else return null
                    else -> return null
                }
            )
            operator = null
        }

        // Deteksi operator berikutnya
        val operatorRegex = Regex("""[+\-*/]""")
        val operatorMatch = operatorRegex.find(equation, match.range.last + 1)
        operator = operatorMatch?.value
    }

    return values.firstOrNull()
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MassConversionCalculatorPreview() {
    CooklyTheme {
        MassConversionCalculator(rememberNavController())
    }
}
