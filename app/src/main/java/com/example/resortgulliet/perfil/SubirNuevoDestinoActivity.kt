package com.example.resortgulliet.perfil

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Destino
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_subir_nuevo_destino.*

class SubirNuevoDestinoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subir_nuevo_destino)

        val userruse = FirebaseAuth.getInstance().currentUser?.email

        buttonSubirDestino.setOnClickListener {
            val destino = Destino(editTextNombreDestino.text.toString(), editTextPaisDestino.text.toString(), editTextGamaDestino.text.toString().toInt(), editTextPrecioDestino.text.toString().toDouble(), editTextURLDestino.text.toString(), userruse.toString())
            val mDatabase = FirebaseDatabase.getInstance().reference
            mDatabase.child("Destinos").child(editTextNombreDestino.text.toString()).setValue(destino)
            Toast.makeText(applicationContext, "Se creo el destino con exito", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}