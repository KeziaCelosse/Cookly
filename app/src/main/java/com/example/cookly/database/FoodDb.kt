
package com.example.cookly.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cookly.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDb : RoomDatabase() {
    abstract fun dao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDb? = null

        fun getInstance(context: Context): FoodDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodDb::class.java,
                        "food.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
