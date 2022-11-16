package edu.uca.innovatech.investigacionroom.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Declaramos que el data class siguiente es un Entity o una tabla en la base de datos
@Entity
data class Ingrediente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nombre")
    val nombre: String,
    @ColumnInfo(name = "tipo")
    val tipo: String,
    @ColumnInfo(name = "humano")
    val humano: Boolean
)
