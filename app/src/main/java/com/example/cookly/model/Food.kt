package com.example.cookly.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gambar: String,
    val category: String,
    val type: String,
    val nama: String,
    val ingredients: String,
    val steps: String
){

    fun doesMacthSearchQuery(query: String) : Boolean{
        val matchingCombinations = listOf(
            nama
        )
        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}