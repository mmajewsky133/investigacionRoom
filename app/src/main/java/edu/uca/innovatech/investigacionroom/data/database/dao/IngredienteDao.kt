package edu.uca.innovatech.investigacionroom.data.database.dao

import androidx.room.*
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente
import kotlinx.coroutines.flow.Flow

//Declaracion que la interface es un dao
@Dao
interface IngredienteDao {

    @Query("SELECT * from ingrediente ORDER BY nombre ASC")
    fun getIngredientes(): Flow<List<Ingrediente>>

    @Query("SELECT * from ingrediente WHERE id = :id")
    fun getIngrediente(id: Int): Flow<Ingrediente>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingrediente: Ingrediente)

    @Update
    suspend fun update(ingrediente: Ingrediente)

    @Delete
    suspend fun delete(ingrediente: Ingrediente)

}