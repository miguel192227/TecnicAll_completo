package com.example.tecnicall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registry.*

class RegistryActivity : AppCompatActivity() {

    private var mIsShowPassRegistry = false
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)
        setup()
        imageEyeStateChanged()
        acceder()
        showPasswordRegisty(mIsShowPassRegistry)
        database = FirebaseDatabase.getInstance()
        dbReference = database.reference.child("User")
    }

    private fun setup(){
        signUpButton.setOnClickListener {
            if (userNameEditText.text.isNotEmpty() && emailRegistryEditText.text.isNotEmpty()
                    && passwordEditText.text.isNotEmpty() && inputRepetirContraseñaRegistro.text.isNotEmpty()){
                if (passwordEditText.text.toString() == inputRepetirContraseñaRegistro.text.toString()
                    && inputRepetirContraseñaRegistro.text.toString() == passwordEditText.text.toString()){
                    if (passwordEditText.length() >= 6 && inputRepetirContraseñaRegistro.length() >=  6){
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailRegistryEditText.text.toString(),
                            passwordEditText.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful){
                                val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
                                val userDB = dbReference.child(user!!.uid)
                                userDB.child("userName").setValue(userNameEditText.text.toString())
                                showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                            }else{
                                showAlert()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Contraseña recomendada más de 6 caracteres", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error creando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun acceder(){
        buttonAcceder.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    private fun imageEyeStateChanged(){
        eyeRegistryImageView.setOnClickListener {
            mIsShowPassRegistry = !mIsShowPassRegistry
            showPasswordRegisty(mIsShowPassRegistry)
        }
        imageViewEyeShowPassRegistry2.setOnClickListener {
            mIsShowPassRegistry = !mIsShowPassRegistry
            showPasswordRegisty(mIsShowPassRegistry)
        }
    }

    private fun showPasswordRegisty(isShow: Boolean){
        if (isShow){
            passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            inputRepetirContraseñaRegistro.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeRegistryImageView.setImageResource(R.drawable.ic_ojooculto)
            imageViewEyeShowPassRegistry2.setImageResource(R.drawable.ic_ojooculto)
        }else{
            passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            inputRepetirContraseñaRegistro.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeRegistryImageView.setImageResource(R.drawable.ic_show_password_24)
            imageViewEyeShowPassRegistry2.setImageResource(R.drawable.ic_show_password_24)
        }
    }
}