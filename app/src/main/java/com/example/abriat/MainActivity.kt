 package com.example.abriat

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

 class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        //partie recherche
        val search = findViewById<View>(R.id.searchbar) as EditText
        search.visibility = View.INVISIBLE
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
          /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()*/
            //affichage de la zone de texte et entrée de la variable de rercherche
           // search.visibility = View.VISIBLE
            when(search.visibility){
                View.INVISIBLE  -> {
                    search.visibility = View.VISIBLE
                }
                View.VISIBLE -> {
                    search.visibility = View.INVISIBLE
                    val request = search.text.toString()   //display the text that you entered in edit text
                   // Snackbar.make(view, request, Snackbar.LENGTH_LONG).setAction("Action", null).show()
                }
            }


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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}