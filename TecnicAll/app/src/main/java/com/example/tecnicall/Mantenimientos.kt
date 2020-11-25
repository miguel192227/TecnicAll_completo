package com.example.tecnicall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_mantenimientos.*

class Mantenimientos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mantenimientos)
        pasar()
        cancel()

    }
    private fun pasar(){
        siguiente.setOnClickListener {
            val opcion1 = Intent(this, Mantenimientos2::class.java)
            startActivity(opcion1)
        }
    }
    private fun cancel(){
        cancelar.setOnClickListener {
            val opcion2 = Intent(this,HomeActivity::class.java)
            startActivity(opcion2)
        }
    }

}