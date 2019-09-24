package com.example.mypokedex.ui.activities

import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import com.example.mypokedex.R
import com.example.mypokedex.ui.adapter.MainAdapter.Companion.TAG_ID_POKEMON
import com.example.mypokedex.ui.contract.DetailContract
import com.example.mypokedex.ui.presenter.DetailPresenter

import android.graphics.BitmapFactory
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.android.synthetic.main.toolbar.*
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import java.net.URL

class DetailActivity : AppCompatActivity(), DetailContract.View {

    companion object {
        val SPEED_NAME = "speed"
        val DEFENSE_NAME = "defense"
        val ATTACK_NAME = "attack"
        val HP_NAME = "hp"
    }

    private lateinit var presenter: DetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        setSupportActionBar(toolbar)

        presenter = DetailPresenter(this)

        if(intent.hasExtra(TAG_ID_POKEMON)) {
            val id = intent.getIntExtra(TAG_ID_POKEMON, 0)
            presenter.getPokemonById(id)
        }

    }

    override fun fetchPokemon(pokemon: Pokemon) {
        runOnUiThread {
            txtName.text = pokemon.name
            txtWeight.text = pokemon.weight.toString()
            txtSize.text = pokemon.height.toString()
            var types = ""
            for(type in pokemon.types) {
                types += "| ${type.type.name}"
            }
            txtType.text = types

            var abilities = ""
            for(ability in pokemon.abilities) {
                abilities += "| ${ability.ability.name} "
            }
            txtAbility.text = abilities

            setStat(pokemon)

            DownLoadImageTask(imvPokemon)
                .execute(pokemon.sprites.frontDefault)
        }
    }

    private fun setStat(pokemon: Pokemon) {
        for (stat in pokemon.stats) {
            when (stat.stat.name) {
                SPEED_NAME -> {
                    txtSpeed.text = stat.baseStat.toString()
                }
                DEFENSE_NAME -> {
                    txtDefense.text = stat.baseStat.toString()
                }
                ATTACK_NAME -> {
                    txtAttack.text = stat.baseStat.toString()
                }
                HP_NAME -> {
                    txtHp.text = stat.baseStat.toString()
                }
            }
        }
    }
}

private class DownLoadImageTask(internal val imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlOfImage = urls[0]
        return try {
            val inputStream = URL(urlOfImage).openStream()
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) { // Catch the download exception
            e.printStackTrace()
            null
        }
    }
    override fun onPostExecute(result: Bitmap?) {
        if(result!=null){
            imageView.setImageBitmap(result)
        }else{
            Toast.makeText(imageView.context,"Error downloading",Toast.LENGTH_SHORT).show()
        }
    }
}
