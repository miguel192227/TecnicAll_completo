package com.example.tecnicall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        pasar()
        pasar2()
        pasar3()

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    private fun setup(email: String, provider: String){
        title = "Inicio"
        signOffHomeButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            if (provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            onBackPressed()
        }
    }
    private fun pasar(){
        image1.setOnClickListener {
            val opcion = Intent(this, Mantenimientos::class.java)
            startActivity(opcion)
        }
    }
    private fun pasar2(){
        image2.setOnClickListener {
            val opcion2 = Intent(this, Reparaciones::class.java)
            startActivity(opcion2)
        }
    }
    private fun pasar3(){
        image3.setOnClickListener {
            val opcion3 = Intent(this, Instalaciones::class.java)
            startActivity(opcion3)
        }
    }
}