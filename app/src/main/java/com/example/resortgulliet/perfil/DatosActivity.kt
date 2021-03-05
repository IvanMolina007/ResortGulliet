package com.example.resortgulliet.perfil

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.resortgulliet.PrincipalActivity
import com.example.resortgulliet.R
import com.example.resortgulliet.inicioCreacion.MainActivity
import com.example.resortgulliet.objetos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_configuracion.*
import kotlinx.android.synthetic.main.activity_datos.*
import kotlinx.android.synthetic.main.fragment_usuario.*

class DatosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        val baseDate = FirebaseDatabase.getInstance().reference.child("usuarios")
        val baseUser = FirebaseAuth.getInstance().currentUser

        baseDate.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val baseRuser = dataSnapshot.getValue(Usuario::class.java)
                    if (baseUser!!.uid == baseRuser!!.id) {
                        val usuario = dataSnapshot.getValue(Usuario::class.java)
                        editTextNameDatos.setText(usuario!!.nombre.toString())
                        textViewSaldoActualDatos.text = usuario.saldo.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        imageButtonAtrasDatos.setOnClickListener {
            finish()
        }

        buttonCambiarNombreDatos.setOnClickListener {
            val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
            val nuevoNombre = editTextNameDatos.text.toString()
            FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("nombre").setValue(nuevoNombre)
        }

        buttonAñadirDinero.setOnClickListener {
            val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
            val saldoActual = textViewSaldoActualDatos.text.toString().toDouble()
            val saldoAnyadir = editTextAñadirSaldoDatos.text.toString().toDouble()
            if (editTextAñadirSaldoDatos.text.isNullOrEmpty() || saldoAnyadir <= 0.0) {
                Toast.makeText(this, "O has dejado añadir saldo vacio o menor/igual a 0", Toast.LENGTH_LONG).show()
            } else {
                FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("saldo").setValue(saldoActual + saldoAnyadir)
            }
        }
    }
}