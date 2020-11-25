package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class Instalaciones : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var result : TextView
    lateinit var nombre : EditText
    lateinit var telefono : EditText
    lateinit var direccion : EditText
    lateinit var marca : EditText
    lateinit var lunes : CheckBox
    lateinit var martes : CheckBox
    lateinit var miercoles : CheckBox
    lateinit var jueves : CheckBox
    lateinit var viernes : CheckBox
    lateinit var sabado : CheckBox
    lateinit var solicitar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instalaciones)

        option = findViewById(R.id.spinner) as Spinner
        result = findViewById(R.id.res_spin) as TextView
        nombre = findViewById(R.id.nom)
        telefono = findViewById(R.id.tel)
        direccion = findViewById(R.id.dire)
        marca = findViewById(R.id.marca)
        lunes = findViewById(R.id.lunes)
        martes = findViewById(R.id.martes)
        miercoles = findViewById(R.id.miercoles)
        jueves = findViewById(R.id.jueves)
        viernes = findViewById(R.id.viernes)
        sabado = findViewById(R.id.sabado)
        solicitar = findViewById(R.id.soli)

        var options = arrayOf("Lavadora","Secadora","Nevecon","Aire acondicionado","Televisor (lcd y led)")

        option.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                result.text = options.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                result.text = "Por favor seleccione un electrodomestico"
            }

        }

    }
    private fun solicitar() {
        val nombre: String = nombre.text.toString()
        val telefono: String = telefono.text.toString()
        val direccion: String = direccion.text.toString()
        val marca: String = marca.text.toString()
        val lunes: CheckBox = lunes
        val martes: CheckBox = martes
        val miercoles: CheckBox = miercoles
        val jueves: CheckBox = jueves
        val viernes: CheckBox = viernes
        val sabado: CheckBox = sabado

        solicitar.setOnClickListener {
            if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && lunes.isChecked) {

            } else if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && martes.isChecked) {
            } else if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && miercoles.isChecked) {
            } else if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && jueves.isChecked) {
            } else if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && viernes.isChecked) {
            } else if (nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && marca.isNotEmpty() && sabado.isChecked) {
            } else {
                Toast.makeText(this, "Hay campos vacios y/o dias sin marcar", Toast.LENGTH_LONG).show()
            }
        }
    }
}