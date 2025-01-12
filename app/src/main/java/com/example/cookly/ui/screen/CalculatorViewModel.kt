package com.example.cookly.ui.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {

    private val _equationText = MutableLiveData("")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    fun onButtonClick(btn: String) {
        Log.i("Clicked Button", btn)

        _equationText.value?.let {
            if (btn == "AC") {
                _equationText.value = ""
                _resultText.value = "0"
                return
            }

            if (btn == "C") {
                if (it.isNotEmpty()) {
                    _equationText.value = it.substring(0, it.length - 1)
                }
                return
            }

            if (btn == "=") {
                val equation = _equationText.value ?: ""
                val convertedEquation = convertUnitsToGrams(equation)
                _resultText.value = calculateResult(convertedEquation) + " g"
                return
            }

            _equationText.value = it + btn
        }
    }

    private fun convertUnitsToGrams(equation: String): String {
        val regex = Regex("([0-9.]+)(kg|g|lb)")
        return regex.replace(equation) { matchResult ->
            val (value, unit) = matchResult.destructured
            val valueInGrams = when (unit) {
                "kg" -> value.toDouble() * 1000 // Convert kg to g
                "g" -> value.toDouble() // g stays as g
                "lb" -> value.toDouble() * 453.592 // Convert lb to g
                else -> value.toDouble()
            }
            valueInGrams.toString()
        }
    }

    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var finalResult =
            context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        if (finalResult.endsWith(".0")) {
            finalResult = finalResult.replace(".0", "")
        }
        return finalResult
    }
}
