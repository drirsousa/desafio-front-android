package com.example.mypokedex.ui.contract

import me.sargunvohra.lib.pokekotlin.model.Pokemon

interface DetailContract {
    interface View {
        fun fetchPokemon(pokemon: Pokemon)
    }

    interface Presenter {
        fun getPokemonById(id: Int)
    }
}