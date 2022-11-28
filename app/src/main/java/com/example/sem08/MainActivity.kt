package com.example.sem08

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sem08.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    //objecto firebase
    private lateinit var auth : FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //init
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.btRegister.setOnClickListener{haceRegistro()}

        binding.btLogin.setOnClickListener{haceLogin()}
    }

    private fun haceRegistro(){
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        Log.d("Registrandose", "Haciendo llamado a creacion")
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d("Registrandose", "Se registro")
                    val user = auth.currentUser
                    refresca(user)
                }else{
                    Log.e("Registrandose", "Error de registro")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_LONG).show()
                    refresca(null)
                }
            }
        Log.d("Registrandose", "sale del proceso...")
    }

    private fun haceLogin(){
        val email = binding.etCorreo.text.toString()
        val clave = binding.etClave.text.toString()

        Log.d("Registrandose", "Haciendo llamado a creacion")
        auth.signInWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d("Autenticando", "Se autentico")
                    val user = auth.currentUser
                    refresca(user)
                }else{
                    Log.e("Autenticando", "Error de autenticacion")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_LONG).show()
                    refresca(null)
                }
            }
        Log.d("Autenticando", "sale del proceso...")
    }

    private fun refresca(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        refresca(usuario)
    }
}