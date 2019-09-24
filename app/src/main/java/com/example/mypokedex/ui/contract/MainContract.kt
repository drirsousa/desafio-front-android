package com.example.mypokedex.ui.contract

import android.content.SharedPreferences
import com.example.mypokedex.data.model.PokemonModel

interface MainContract {
    interface View {
        fun fetchLocalPokemons(pokemons: ArrayList<PokemonModel>)
    }

    interface Presenter {
        fun getLocalPokemons(mPrefs: SharedPreferences)
    }
}