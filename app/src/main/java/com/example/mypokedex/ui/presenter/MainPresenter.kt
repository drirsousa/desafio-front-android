package com.example.mypokedex.ui.presenter

import android.content.SharedPreferences
import com.example.mypokedex.data.PokedexRepository
import com.example.mypokedex.ui.contract.MainContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainPresenter (val view: MainContract.View) : MainContract.Presenter {
    val repository = PokedexRepository()

    override fun getLocalPokemons(mPrefs: SharedPreferences) {
        GlobalScope.launch {
            val pokemonSpecies = repository.getLocalPokemons(mPrefs).await()
            view.fetchLocalPokemons(pokemonSpecies)
        }
    }
}