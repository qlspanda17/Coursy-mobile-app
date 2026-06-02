package com.example.coursy

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.coursy.databinding.ActivityMainBinding




/*class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}*/


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Charge le layout et permet d'acceder aux éléments
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(acceuil())

        // Ecoute les clics sur la barre du bas
        binding.BottomNavigationView.setOnItemSelectedListener{

            // Détecte le bouton cliquer
            when(it.itemId){

                R.id.acceuil -> replaceFragment(acceuil())
                R.id.historique -> replaceFragment(historique())
                R.id.suggestions -> replaceFragment(suggestions())
                R.id.parametre -> replaceFragment(parametre())

                else ->{

                }

            }
            // Indique que l'évènement est bien géré
            true

        }



    }

    // Fonction pour changer d'écran
    private fun replaceFragment(fragment: Fragment) {
        // Récupération du gestionnaire de fragment
        val fragmentManager = supportFragmentManager
        // Je commence la modification
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Remplace le framelaoyout par le fragment demandé
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        // J'applique le changement
        fragmentTransaction.commit()

    }
}