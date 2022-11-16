package edu.uca.innovatech.investigacionroom

import android.app.Application
import edu.uca.innovatech.investigacionroom.data.database.IngredienteRoomDatabase

class IngredienteApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: IngredienteRoomDatabase by lazy { IngredienteRoomDatabase.getDatabase(this) }
}