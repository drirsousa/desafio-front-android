package com.example.mypokedex.ui.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypokedex.R
import com.example.mypokedex.data.model.PokemonModel
import com.example.mypokedex.ui.activities.DetailActivity.Companion.ATTACK_NAME
import com.example.mypokedex.ui.activities.DetailActivity.Companion.DEFENSE_NAME
import com.example.mypokedex.ui.activities.DetailActivity.Companion.HP_NAME
import com.example.mypokedex.ui.activities.DetailActivity.Companion.SPEED_NAME
import com.example.mypokedex.ui.activities.MainActivity.Companion.PREFS_NAME
import com.example.mypokedex.ui.contract.AddContract
import com.example.mypokedex.ui.presenter.AddPresenter
import kotlinx.android.synthetic.main.activity_pokemon_add.*
import kotlinx.android.synthetic.main.toolbar.*
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.PokemonStat


class AddActivity : AppCompatActivity(), AddContract.View {

    private lateinit var presenter: AddContract.Presenter
    private lateinit var myPokemon: Pokemon
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_add)
        setSupportActionBar(toolbar)

        presenter = AddPresenter(this)

        txtName.setOnFocusChangeListener { view, hasFocus ->
            if(!hasFocus) {
                val editText = view as EditText
                if(!editText.text.isEmpty()) {
                    presenter.getPokemonByName(editText.text.toString())
                }
            }
        }

        btnSave.setOnClickListener {
            val statsList = getStats()

            val pokemon = PokemonModel(myPokemon.id, myPokemon.name,myPokemon.baseExperience
                , txtSize.text.toString().toInt(), myPokemon.isDefault, myPokemon.order, txtWeight.text.toString().toInt(), myPokemon.species, myPokemon.abilities
                , myPokemon.forms, myPokemon.gameIndices, myPokemon.heldItems, myPokemon.moves, statsList, myPokemon.types, myPokemon.sprites)

            val preferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            presenter.addPokemon(preferences, pokemon)
        }

    }

    private fun getStats() : List<PokemonStat> {
        val speedStat = myPokemon.stats.filter { it.stat.name == SPEED_NAME }.first()
        val speed = PokemonStat(speedStat.stat, speedStat.effort, txtSpeed.text.toString().toInt())

        val defenseStat = myPokemon.stats.filter { it.stat.name == DEFENSE_NAME }.first()
        val defense = PokemonStat(defenseStat.stat, defenseStat.effort, txtDefense.text.toString().toInt())

        val attackStat = myPokemon.stats.filter { it.stat.name == ATTACK_NAME }.first()
        val attack = PokemonStat(attackStat.stat, attackStat.effort, txtAttack.text.toString().toInt())

        val hpStat = myPokemon.stats.filter { it.stat.name == HP_NAME }.first()
        val hp = PokemonStat(hpStat.stat, hpStat.effort, txtHp.text.toString().toInt())

        return listOf(speed, defense, attack, hp)
    }

    override fun setId(id: Int) {
        if(id == 0) {
            runOnUiThread {
                Toast.makeText(this, getString(R.string.msg_no_exists), Toast.LENGTH_SHORT).show()
            }
        } else {
            presenter.getPokemonById(id)
        }
    }

    override fun fetchTypeAndAbility(pokemon: Pokemon) {
        runOnUiThread {
            myPokemon = pokemon

            var types = ""
            for(type in pokemon.types) {
                types += "| ${type.type.name}"
            }
            txtType.text = types

            var abilities = ""
            for(ability in pokemon.abilities) {
                abilities += "| ${ability.ability.name} "
            }
        }
    }

    override fun addResult(isSaved: Boolean) {
        runOnUiThread {
            var message = ""
            if(isSaved) {
                message = getString(R.string.msg_pokemon_saved)
            } else {
                message = getString(R.string.msg_pokemon_not_saved)
            }

            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.lbl_attention))
            builder.setMessage(message)
            builder.setNeutralButton(getString(R.string.lbl_ok), DialogInterface.OnClickListener { _, i ->
                if(isSaved) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    dialog.dismiss()
                }
            })

            dialog = builder.create()
            dialog.show()
        }
    }
}
