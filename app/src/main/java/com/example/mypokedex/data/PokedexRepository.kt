package com.example.mypokedex.data

import android.content.SharedPreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import com.google.gson.Gson
import com.example.mypokedex.data.model.PokemonModel


class PokedexRepository {

    companion object {
        val POKEMON = "pokemon"
    }

    var pokeApiClient = PokeApiClient()

    fun getPokemonList() = GlobalScope.async {
        pokeApiClient.getPokemonList(100,100)
    }

    fun getLocalPokemons(mPrefs: SharedPreferences) = GlobalScope.async {
        val gson = Gson()
        val all = mPrefs.all
        val pokemonList = ArrayList<PokemonModel>()
        for (key in all.keys) {
            val json = mPrefs.getString(key, "")
            val obj = gson.fromJson(json, PokemonModel::class.java)
            pokemonList.add(obj)
        }
        pokemonList
    }

    fun getPokemonById(id: Int) = GlobalScope.async {
        pokeApiClient.getPokemon(id)
    }

    fun addPokemon(mPrefs: SharedPreferences, pokemon: PokemonModel) = GlobalScope.async {
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(pokemon)
        val name = "$POKEMON-${pokemon.id}"
        prefsEditor.putString(name, json)
        prefsEditor.commit()
    }

    fun hasPokemon(mPrefs: SharedPreferences, pokemon: PokemonModel) : Boolean {
        val gson = Gson()
        val name = "$POKEMON-${pokemon.id}"
        val json = mPrefs.getString(name, "")
        val result = gson.fromJson(json, PokemonModel::class.java!!)
        return result != null
    }
}