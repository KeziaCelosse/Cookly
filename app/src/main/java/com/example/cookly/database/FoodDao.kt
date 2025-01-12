
package com.example.cookly.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cookly.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: Food)

    @Update
    suspend fun update(food: Food)

    @Query("SELECT * FROM food ORDER BY nama ASC")
    fun getFood(): Flow<List<Food>>

    @Query("SELECT * FROM food WHERE id = :id")
    suspend fun getFoodById(id: Long): Food?

    @Query("DELETE FROM food WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM Food")
    fun getAllFoods(): Flow<List<Food>>

//    @Delete
//    suspend fun deleteFood(food: Food)

}
