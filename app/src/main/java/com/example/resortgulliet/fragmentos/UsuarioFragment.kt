package com.example.resortgulliet.fragmentos

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resortgulliet.R
import com.example.resortgulliet.adapter.FacturasAdapter
import com.example.resortgulliet.adapter.ViajesAdapter
import com.example.resortgulliet.inicioCreacion.MainActivity
import com.example.resortgulliet.objetos.Destino
import com.example.resortgulliet.objetos.Factura
import com.example.resortgulliet.objetos.Usuario
import com.example.resortgulliet.perfil.ConfiguracionActivity
import com.example.resortgulliet.perfil.DatosActivity
import com.example.resortgulliet.perfil.SubirNuevoDestinoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_configuracion.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_usuario.*
import java.util.ArrayList

class UsuarioFragment : Fragment() {
    lateinit var facturasList: ArrayList<Factura>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val baseDate = FirebaseDatabase.getInstance().reference.child("usuarios")
        val baseUser = FirebaseAuth.getInstance().currentUser

        baseDate.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot: DataSnapshot in snapshot.children) {
                    val baseRuser = dataSnapshot.getValue(Usuario::class.java)
                    if (baseUser!!.uid == baseRuser!!.id) {
                        val usuario = dataSnapshot.getValue(Usuario::class.java)
                        if (textViewNombreUsuario != null) {
                            textViewNombreUsuario.text = usuario!!.nombre
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        buttonDatosUsuario.setOnClickListener {
            val nextActivityIntent = Intent(activity, DatosActivity::class.java)
            startActivity(nextActivityIntent)
            activity?.overridePendingTransition(R.anim.left_in, R.anim.left_out)
        }

        buttonConfigUsuario.setOnClickListener {
            val nextActivityIntent = Intent(activity, ConfiguracionActivity::class.java)
            startActivity(nextActivityIntent)
            activity?.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }

        buttonSignOutUsuario.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(activity, "Se cerro sesion satisfactoriamente", Toast.LENGTH_LONG).show()
            val nextActivityIntent = Intent(activity, MainActivity::class.java)
            startActivity(nextActivityIntent)
            activity?.finish()
        }

        buttonSubirUsuario.setOnClickListener {
            val nextActivityIntent = Intent(activity, SubirNuevoDestinoActivity::class.java)
            startActivity(nextActivityIntent)
            activity?.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }

        facturasList = ArrayList<Factura>()
        val baseDatee = FirebaseDatabase.getInstance().reference.child("Facturas")

        recyclerFacturas.layoutManager = LinearLayoutManager(context)

        recyclerFacturas.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        val valueLise: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (recyclerFacturas != null) {
                    facturasList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val messagesRuser = dataSnapshot.getValue(Factura::class.java)
                        if (messagesRuser!!.idUser.equals(FirebaseAuth.getInstance().currentUser!!.uid)) {
                            facturasList.add(messagesRuser!!)
                        }
                    }
                    val adapter = FacturasAdapter(facturasList)
                    recyclerFacturas.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        baseDatee.addValueEventListener(valueLise)
    }
}