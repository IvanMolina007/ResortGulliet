package com.example.resortgulliet.perfil

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_configuracion.*

class ConfiguracionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val baseDate = FirebaseDatabase.getInstance().reference.child("usuarios")
        val baseUser = FirebaseAuth.getInstance().currentUser

        baseDate.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val baseRuser = dataSnapshot.getValue(Usuario::class.java)
                    if (baseUser!!.uid == baseRuser!!.id) {
                        val usuario = dataSnapshot.getValue(Usuario::class.java)
                        when (usuario!!.moneda) {
                            //Tipo 1 -- Euro
                            1 -> {radioButton.isChecked = true
                            }
                            //Tipo 2 -- Corona Checa
                            2 -> {radioButton2.isChecked = true
                            }
                            //Tipo 3 -- Libra Esterlina
                            3 -> {radioButton3.isChecked = true
                            }
                            //Tipo 4 -- Zloty (Polonia)
                            4 -> {radioButton4.isChecked = true
                            }
                            //Tipo 5 -- Rublo Ruso
                            5-> {radioButton5.isChecked = true
                            }
                            //Tipo 6 -- Franco Suizo
                            6-> {radioButton6.isChecked = true
                            }
                            //Tipo 7 -- Florin Hungaro
                            7-> {radioButton7.isChecked = true
                            }
                        }

                        editTextNacioConfig.setText(usuario!!.nacionalidad.toString())
                        textViewConfigSaldo.text = usuario.moneda?.let { usuario.saldo?.let { it1 ->
                            usuario.id?.let { it2 ->
                                cambiarDivisas(it,
                                    it1,
                                    it2
                                ).toString()
                            }
                        } }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        imageButtonConfigEnd.setOnClickListener {
            finish()
        }
    }

    private fun cambiarDivisas(tipo: Int, saldo: Double, id: String): Double {
        var saldoNuevo = 0.0

        when (tipo) {
            //Tipo 1 -- Euro
            1 -> {saldoNuevo = saldo
            }
            //Tipo 2 -- Corona Checa
            2 -> {saldoNuevo = saldo * 23.73
            }
            //Tipo 3 -- Libra Esterlina
            3 -> {saldoNuevo = saldo * 0.88
            }
            //Tipo 4 -- Zloty (Polonia)
            4 -> {saldoNuevo = saldo * 4.5
            }
            //Tipo 5 -- Rublo Ruso
            5-> {saldoNuevo = saldo * 89.27
            }
            //Tipo 6 -- Franco Suizo
            6-> {saldoNuevo = saldo * 1.08
            }
            //Tipo 7 -- Florin Hungaro
            7-> {saldoNuevo = saldo * 358.64
            }
        }
        return saldoNuevo
    }

    fun listenerAplicar(view: View) {
        val usuarioID = FirebaseAuth.getInstance().currentUser?.uid
        val nuevaNacionalidad = editTextNacioConfig.text.toString()
        FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("nacionalidad").setValue(nuevaNacionalidad)

        if (radioButton.isChecked) {
            FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(1)
        } else {
            if (radioButton2.isChecked) {
                FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(2)
            } else {
                if (radioButton3.isChecked) {
                    FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(3)

                } else {
                    if (radioButton4.isChecked) {
                        FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(4)
                    } else {
                        if (radioButton5.isChecked) {
                            FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(5)
                        } else {
                            if (radioButton6.isChecked) {
                                FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(6)
                            } else {
                                if (radioButton7.isChecked) {
                                    FirebaseDatabase.getInstance().reference.child("usuarios").child(usuarioID!!).child("moneda").setValue(7)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}