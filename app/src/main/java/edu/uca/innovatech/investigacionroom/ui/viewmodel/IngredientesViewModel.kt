package edu.uca.innovatech.investigacionroom.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.uca.innovatech.investigacionroom.data.database.dao.IngredienteDao
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente
import kotlinx.coroutines.launch

class IngredientesViewModel(private val ingredienteDao: IngredienteDao) : ViewModel() {

    val allIngredientes: LiveData<List<Ingrediente>> = ingredienteDao.getIngredientes().asLiveData()

    private fun insertIngrediente(ingrediente: Ingrediente) {
        viewModelScope.launch {
            ingredienteDao.insert(ingrediente)
        }
    }

    fun agregarIngrediente(nombre: String, tipo: String){
        val nuevoIngrediente = Ingrediente(
            nombre = nombre,
            tipo = tipo
        )
        insertIngrediente(nuevoIngrediente)
    }

    fun entradasValidas(nombre: String, tipo: String): Boolean {
        if (nombre.isBlank() || tipo.isBlank()) {
            return false
        }
        return true
    }

}

//Clase Factory para instansear la instanciade ViewModel
class IngredientesViewModelFactory(private val ingredienteDao: IngredienteDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientesViewModel(ingredienteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}