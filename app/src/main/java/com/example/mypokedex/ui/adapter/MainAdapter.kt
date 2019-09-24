package com.example.mypokedex.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R
import com.example.mypokedex.data.model.PokemonModel
import com.example.mypokedex.ui.activities.DetailActivity
import java.util.ArrayList

class MainAdapter (val pokemonList: ArrayList<PokemonModel>, val context: Context) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    companion object {
        const val TAG_ID_POKEMON = "idPokemon"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.bindItems(pokemon)

        holder.itemView.setOnClickListener {
            goToDetailActivity(pokemon)
        }
    }

    private fun goToDetailActivity(pokemon: PokemonModel) {
        val intent = Intent(context, DetailActivity::class.java)

        intent.putExtra(TAG_ID_POKEMON, pokemon.id)
        context.startActivity(intent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(pokemon: PokemonModel) {
            val txtName = itemView.findViewById(R.id.txtName) as TextView

            txtName.setText(pokemon.name)
        }
    }
}