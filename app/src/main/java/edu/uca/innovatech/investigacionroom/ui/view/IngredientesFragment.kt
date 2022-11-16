package edu.uca.innovatech.investigacionroom.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import edu.uca.innovatech.investigacionroom.IngredienteApplication
import edu.uca.innovatech.investigacionroom.R
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente
import edu.uca.innovatech.investigacionroom.databinding.FragmentIngredientesBinding
import edu.uca.innovatech.investigacionroom.ui.viewmodel.IngredientesViewModel
import edu.uca.innovatech.investigacionroom.ui.viewmodel.IngredientesViewModelFactory

class IngredientesFragment : Fragment() {

    //Basicamente comparte el ViewModel entre fragmentos
    private val viewModel: IngredientesViewModel by activityViewModels {
        IngredientesViewModelFactory(
            (activity?.application as IngredienteApplication).database
                .IngredienteDao()
        )
    }

    //un lateinit var para las entidades a usar (en este caso solo la entidad ingrediente)
    lateinit var ingrediente: Ingrediente

    //el view binding
    private var _binding: FragmentIngredientesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Funcion para validar que los Edit Texts no esten vacios
    private fun entradasValidas(): Boolean {
        return viewModel.entradasValidas(
            binding.etNombre.text.toString(),
            binding.etTipo.text.toString()
        )
    }

    //Funcion para mandar a agregar un ingrediente
    private fun agregarNuevoIngrediente() {
        if (entradasValidas()) {
            viewModel.agregarIngrediente(
                binding.etNombre.text.toString(),
                binding.etTipo.text.toString()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAgregar.setOnClickListener {
            agregarNuevoIngrediente()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}