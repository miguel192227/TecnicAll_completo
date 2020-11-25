package com.example.tecnicall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private var mIsShowPass = false
    private val GOOGLE_SIGN_IN = 100
    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var auth: FirebaseAuth
    //private lateinit var txtEmail: EditText
    //private lateinit var txtPassword: EditText
    //private lateinit var btnLogIn: Button
    //private lateinit var imgGoogle: ImageView
    //private lateinit var imgFacebook: ImageView
    //private lateinit var txtGoRegistry: TextView
    //private lateinit var txtForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()
        //txtEmail= findViewById(R.id.emailAuthEditText)
        //txtPassword = findViewById(R.id.passwordAuthEditText)
        //btnLogIn = findViewById(R.id.logInAuthButton)
        //imgGoogle = findViewById(R.id.googleAuthImageView)
        //imgFacebook = findViewById(R.id.facebookAuthImageView)
        //txtGoRegistry = findViewById(R.id.goRegistryAuthTextView)
        //txtForgotPassword = findViewById(R.id.forgotPasswordAuthTextView)
        Thread.sleep(2000)
        setTheme(R.style.AppThemeLogin)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        logIn()
        activityRegistry()
        forgotPassword()
        showPassword(mIsShowPass)
        imageEyeStateChanged()
        session()
        containerPrincipalConstraintLayout.visibility = View.VISIBLE
    }

    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)
        if (email != null && provider != null){
            containerPrincipalConstraintLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }



    private fun logIn(){
        //val email: String = txtEmail emailAuthEditText.text.toString()
        //val password: String = txtPassword passwordAuthEditText.text.toString()
        //val google: ImageView = imgGoogle
        //val facebook: ImageView = imgFacebook
        logInAuthButton.setOnClickListener {

            if (emailAuthEditText.text.toString().isNotEmpty() && passwordAuthEditText.text.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(emailAuthEditText.text.toString(), passwordAuthEditText.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    }else{
                        showAlert()
                    }
                }
            }else{
                Toast.makeText(this, "Hay campos vacios", Toast.LENGTH_LONG).show()
            }

            // Instantiate the RequestQueue.


        }
        googleAuthImageView.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
        googleAuthImageView.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            auth.signInWithCredential(credential).addOnCompleteListener {
                                if (it.isSuccessful){
                                    showHome(it.result?.user?.email ?: "", ProviderType.FACEBOOK)
                                }else{
                                    showAlertMessage()
                                }
                            }
                        }
                    }

                    override fun onCancel() {

                    }

                    override fun onError(error: FacebookException?) {
                        showAlert()
                    }
                })
        }
    }

    private fun showHome(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando el usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlertMessage(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cancelada la operación")
        builder.setMessage("Se cancelo la opcion de inicio de sesion con Facebook")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun activityRegistry(){
        //val registry: TextView = txtGoRegistry
        goRegistryAuthTextView.setOnClickListener {
            val registryIntent = Intent(this, RegistryActivity::class.java)
            startActivity(registryIntent)
        }
    }

    private fun forgotPassword(){
        //val forgotPassword: TextView = txtForgotPassword
        forgotPasswordAuthTextView.setOnClickListener {
            val intent = Intent(this, PopUpActivity::class.java)
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)
        }
    }

    private fun showPassword(isShow: Boolean){
        if (isShow){
            passwordAuthEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            eyeAuthImageView.setImageResource(R.drawable.ic_ojooculto)
        }else{
            passwordAuthEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            eyeAuthImageView.setImageResource(R.drawable.ic_show_password_24)
        }
        passwordAuthEditText.setSelection(passwordAuthEditText.text.toString().length)
    }

    private fun imageEyeStateChanged(){
        eyeAuthImageView.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(account.email ?: "", ProviderType.GOOGLE)
                        }else{
                            Toast.makeText(this, "Se cancelo la opcion de inicio de sesion con Google", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }catch (e: ApiException){
                showAlert()
            }
        }
    }
}