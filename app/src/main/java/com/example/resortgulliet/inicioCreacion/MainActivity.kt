package com.example.resortgulliet.inicioCreacion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resortgulliet.PrincipalActivity
import com.example.resortgulliet.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(applicationContext, PrincipalActivity::class.java))
            finish()
        }
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCrearHiper.setOnClickListener {
            val nextActivityIntent = Intent(this, CreateActivity::class.java)
            startActivity(nextActivityIntent)
        }

        textViewIniciarSesion.setOnClickListener {
            inicioDeSesion()
        }
    }

    private fun inicioDeSesion() {
        if (editTextCorreoInicio.text.toString().isEmpty() || editTextCorreoInicio.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Has dejado alguno de los campos en blanco", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                editTextCorreoInicio.text.toString(),
                editTextContraInicio.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Ha iniciado Sesion satisfactoriamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, PrincipalActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "No pudo iniciar sesion, revise su correo y contraseña", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}