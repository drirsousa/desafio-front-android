package com.example.mypokedex.ui.contract

import android.content.SharedPreferences
import com.example.mypokedex.data.model.PokemonModel
import me.sargunvohra.lib.pokekotlin.model.Pokemon

interface AddContract {
    interface View {
        fun addResult(isSaved: Boolean)
        fun setId(id: Int)
        fun fetchTypeAndAbility(pokemon: Pokemon)
    }

    interface Presenter {
        fun addPokemon(mPrefs: SharedPreferences, pokemon: PokemonModel)
        fun getPokemonByName(name: String)
        fun getPokemonById(id: Int)
    }
}