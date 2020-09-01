package com.example.tecnicall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.AppThemeLogin)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtIrRegistro = findViewById<TextView>(R.id.irRegistroTextView)

        txtIrRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }
}