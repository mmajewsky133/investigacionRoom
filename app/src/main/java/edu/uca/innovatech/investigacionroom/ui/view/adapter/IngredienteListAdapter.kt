package edu.uca.innovatech.investigacionroom.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uca.innovatech.investigacionroom.data.database.entities.Ingrediente
import edu.uca.innovatech.investigacionroom.databinding.IngredienteListItemBinding

class IngredienteListAdapter(private val onIngredienteClicked: (Ingrediente) -> Unit) :
    ListAdapter<Ingrediente, IngredienteListAdapter.IngredienteViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteViewHolder {
        val binding = IngredienteListItemBinding.inflate(
            LayoutInflater.from( parent.context )
        )
        return IngredienteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredienteViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onIngredienteClicked(current)
        }
        holder.bind(current)
    }


    class IngredienteViewHolder(private val binding: IngredienteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingrediente: Ingrediente) {
            binding.tvIngrediente.text = ingrediente.nombre
            binding.tvTipo.text = ingrediente.tipo
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Ingrediente>() {
            override fun areItemsTheSame(oldItem: Ingrediente, newItem: Ingrediente): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Ingrediente, newItem: Ingrediente): Boolean {
                return oldItem.nombre == newItem.nombre
            }
        }
    }
}