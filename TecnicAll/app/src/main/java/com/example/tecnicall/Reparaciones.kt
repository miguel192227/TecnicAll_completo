package com.example.tecnicall

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_reparaciones.*

class Reparaciones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reparaciones)
        val spinner : Spinner = findViewById(R.id.spinner)
        enviar()

        ArrayAdapter.createFromResource(this,R.array.electro2, android.R.layout.simple_spinner_item).also{
                arrayAdapter -> arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = arrayAdapter
        }
    }
    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener{
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            spinner.onItemSelectedListener = this
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }
    }
    private fun enviar(){
        soli.setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.1.18/EnvioCorreo/Mailer.php?para=miguelangelpabonherrera@gmail.com&mensaje=Electrodomestico: Televisor - Nombre: Cesar Pabon - Telefono:320 704 8472 - Direccion: Barrio la milagrosa - Marca: Lg - Dia(s) disponible(s): Martes, Miercoles"

            // Request a string response from the provided URL.
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> { response ->

                    Toast.makeText(this,  "Enviado", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener  { error ->   error.printStackTrace()})

            // Add the request to the RequestQueue.
            queue.add(stringRequest)
        }
    }
}