package com.example.mypokedex.ui.presenter

import android.content.SharedPreferences
import com.example.mypokedex.data.PokedexRepository
import com.example.mypokedex.data.model.PokemonModel
import com.example.mypokedex.ui.contract.AddContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AddPresenter (val view: AddContract.View) : AddContract.Presenter {
    val repository = PokedexRepository()

    override fun addPokemon(mPrefs: SharedPreferences, pokemon: PokemonModel) {
        GlobalScope.launch {
            repository.addPokemon(mPrefs, pokemon).await()
            view.addResult(repository.hasPokemon(mPrefs, pokemon))
        }
    }

    override fun getPokemonByName(name: String) {
        GlobalScope.launch {
            var id = 0
            val pokemonList = repository.getPokemonList().await().results
            val listFiltered = pokemonList.filter { it.name == name }
            if(listFiltered.isNotEmpty()) {
                val pokemon = listFiltered.first()
                id = pokemon.id
            }
            view.setId(id)
        }
    }

    override fun getPokemonById(id: Int) {
        GlobalScope.launch {
            val pokemon = repository.getPokemonById(id).await()
            view.fetchTypeAndAbility(pokemon)
        }
    }
}