package com.example.cookly.ui.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.database.FoodDao
import com.example.cookly.model.Food
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DetailViewModel(private val dao: FoodDao): ViewModel() {

    fun insert(
        gambar: String,
        selectedCategory: String,
        selectedType: String,
        nama: String,
        ingredients: String,
        steps: String
    ) {
        val newfood = Food(
            gambar = gambar,
            type = selectedType,
            category = selectedCategory,
            nama = nama,
            ingredients = ingredients,
            steps = steps
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.insert(Food(
                    gambar = gambar,
                    nama = nama,
                    type = selectedType,
                    category = selectedCategory,
                    ingredients = ingredients,
                    steps = steps
                ))
                println("Data berhasil disimpan")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Gagal menyimpan data: ${e.message}")
            }
        }
    }

    fun update(
        id: Long,
        gambar: String,
        selectedType: String,
        selectedCategory: String,
        nama: String,
        ingredients: String,
        steps: String,
    ) {
        val food = Food(
            id = id,
            gambar = gambar,
            type = selectedType,
            category = selectedCategory,
            nama = nama,
            ingredients = ingredients,
            steps = steps
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(food)
        }
    }

    suspend fun getFood(id: Long): Food? {
        return dao.getFoodById(id)
    }

    fun saveImageToInternalStorage(context: Context, bitMap: Bitmap, filename: String): Uri {
        val file = File(context.filesDir, filename)

        FileOutputStream(file).use {
            bitMap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return Uri.fromFile(file)
    }

//    fun getAllFoods() = dao.getAllFoods()
    fun getAllFoods(): Flow<List<Food>> {
        return dao.getAllFoods().map { foodList ->
            foodList.sortedByDescending { it.id } // Urutkan berdasarkan ID secara menurun (reses terbaru di atas)
        }
    }


}