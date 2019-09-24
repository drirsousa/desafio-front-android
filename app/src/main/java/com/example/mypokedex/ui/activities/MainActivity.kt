package com.example.mypokedex.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R
import com.example.mypokedex.data.model.PokemonModel
import com.example.mypokedex.ui.adapter.MainAdapter
import com.example.mypokedex.ui.contract.MainContract
import com.example.mypokedex.ui.presenter.MainPresenter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*



class MainActivity : AppCompatActivity(), MainContract.View {

    companion object {
        val PREFS_NAME = "MODEL_PREFERENCES"
    }

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvPokemon.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        presenter = MainPresenter(this)

        val preferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        presenter.getLocalPokemons(preferences)
    }

    override fun fetchLocalPokemons(pokemons: ArrayList<PokemonModel>) {
        runOnUiThread {
            adapter = MainAdapter(pokemons, this)
            rvPokemon.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(this, AddActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}