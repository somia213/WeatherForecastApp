package com.example.wheatherapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
// when change in your Entity(table) -> change version to prevent conflict occurrence
abstract class FavouriteDataBase :RoomDatabase(){
     abstract fun favouriteDao():FavouriteDao

     companion object {
          @Volatile
          // Volatile --> to be UpToDate
          private var instance: FavouriteDataBase? = null
          private val LOCK = Any()

          operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
               // synchronized --> to avoid interruption if two thread call the same method
               instance ?: createDatabase(context).also { instance = it }
          }


          private fun createDatabase(context: Context) =
               Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteDataBase::class.java,
                    "favourite.db"
               )
                    .fallbackToDestructiveMigration()
                    .build()
          }
}