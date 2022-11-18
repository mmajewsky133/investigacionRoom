package edu.uca.innovatech.investigacionroom.data.database

import android.content.ClipData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.uca.innovatech.investigacionroom.data.database.dao.IngredienteDao
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente

//Declaramos que es una database esete abstract class
@Database(entities = [Ingrediente::class], version = 1, exportSchema = false)
abstract class IngredienteRoomDatabase : RoomDatabase() {

    abstract fun IngredienteDao(): IngredienteDao

    companion object {
        @Volatile
        private var INSTANCE: IngredienteRoomDatabase? = null

        fun getDatabase(context: Context): IngredienteRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IngredienteRoomDatabase::class.java,
                    "ingrediente_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}