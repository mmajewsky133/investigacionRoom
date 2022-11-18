package edu.uca.innovatech.investigacionroom.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.uca.innovatech.investigacionroom.IngredienteApplication
import edu.uca.innovatech.investigacionroom.R
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente
import edu.uca.innovatech.investigacionroom.databinding.FragmentIngredienteDetalleBinding
import edu.uca.innovatech.investigacionroom.ui.viewmodel.IngredientesViewModel
import edu.uca.innovatech.investigacionroom.ui.viewmodel.IngredientesViewModelFactory

class IngredienteDetalleFragment : BottomSheetDialogFragment() {

    lateinit var ingrediente: Ingrediente

    //Basicamente comparte el ViewModel entre fragmentos
    private val viewModel: IngredientesViewModel by activityViewModels {
        IngredientesViewModelFactory(
            (activity?.application as IngredienteApplication).database
                .IngredienteDao()
        )
    }

    //el view binding
    private var _binding: FragmentIngredienteDetalleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIngredienteDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = this.tag?.toInt()

        if (id != null) {
            viewModel.agarrarIngrediente(id)
                .observe(this.viewLifecycleOwner) { selectedIngrediente ->
                    selectedIngrediente?.let{
                        ingrediente = it
                        bind(ingrediente)
                    }
                }
        }
    }

    private fun bind(ingrediente: Ingrediente) {
        binding.apply {
            tvIngrediente.text = ingrediente.nombre
            etIngrediente.setText(ingrediente.nombre)
            tvTipo.text = ingrediente.tipo
            etTipo.setText(ingrediente.tipo)

            btnEditar.setOnClickListener { editarIngrediente() }
            btnEliminar.setOnClickListener { mostrarDialogConfirmacion() }
        }
    }

    private fun editarIngrediente() {

        val id = this.tag?.toInt()

        changeLayout()

        binding.btnEditar.setOnClickListener {
            if (entradasValidas()) {
                if (id != null) {
                    viewModel.editarIngrediente(id,
                        binding.etIngrediente.text.toString(),
                        binding.etTipo.text.toString()
                    )
                }
            }
            dismiss()
        }
    }

    private fun changeLayout(){
        binding.apply {
            tvIngrediente.isVisible = false
            tvTipo.isVisible = false
            etlIngrediente.isVisible = true
            etlTipo.isVisible = true
            btnEliminar.isEnabled = false
            btnEditar.setText(R.string.guardar)
            etIngrediente.requestFocus()
        }
    }

    private fun mostrarDialogConfirmacion() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Quiere eliminar el siguiente ingrediente?")
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                eliminarIngrediente()
            }
            .show()
    }

    private fun eliminarIngrediente() {
        viewModel.eliminarIngrediente(ingrediente)
        dismiss()
    }

    private fun entradasValidas(): Boolean {
        return viewModel.entradasValidas(
            binding.etIngrediente.text.toString(),
            binding.etTipo.text.toString()
        )
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}