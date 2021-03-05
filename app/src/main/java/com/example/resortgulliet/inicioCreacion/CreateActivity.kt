package com.example.resortgulliet.inicioCreacion

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create.*

class CreateActivity : AppCompatActivity() {
    private var saldo = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        buttonVolver.setOnClickListener {
            finish()
        }
        buttonCrear.setOnClickListener {
            if (editTextCrearCorreo.text.toString().isEmpty() || editTextCrearContraseña.text.toString().isEmpty() || editTextCreateName.text.toString().isEmpty()) {
                Toast.makeText(this, "Correo, contraseña o nombre se dejaron vacías", Toast.LENGTH_LONG).show()
            } else {
                if (editTextCreateSaldo.text.toString().isNotEmpty()) {
                    saldo = editTextCreateSaldo.text.toString().toDouble()
                }
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(editTextCrearCorreo.text.toString(),editTextCrearContraseña.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext,"El usuario se creó satisfactoriamente",Toast.LENGTH_SHORT).show()

                        nuevoUsuario(auth.currentUser!!.uid, editTextCreateName.text.toString(),
                            editTextCreateNacio.text.toString(), saldo, editTextCrearCorreo.text.toString(), 1)
                        finish()
                    } else {
                        Toast.makeText(applicationContext,"Usuario no pudo crearse, observe que" +
                                " haya mas de 5 digitos en la contraseña o revisa otro campo",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //Hacer el boton subir en el inicio, onClick subiendo el nuevo usuario

    private fun nuevoUsuario(id: String, nombre: String, nacionalidad: String, saldo: Double, correo: String, moneda: Int) {
        val mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("usuarios").child(id).setValue(Usuario(id, nombre, nacionalidad, saldo, correo, moneda))
    }
}