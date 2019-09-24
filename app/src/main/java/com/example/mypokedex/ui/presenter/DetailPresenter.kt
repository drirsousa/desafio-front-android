package com.example.mypokedex.ui.presenter

import com.example.mypokedex.data.PokedexRepository
import com.example.mypokedex.ui.contract.DetailContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailPresenter (val view: DetailContract.View) : DetailContract.Presenter {
    val repository = PokedexRepository()

    override fun getPokemonById(id: Int) {
        GlobalScope.launch {
            val pokemon = repository.getPokemonById(id).await()
            view.fetchPokemon(pokemon)
        }
    }
}