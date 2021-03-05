package com.example.resortgulliet.fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resortgulliet.R
import com.example.resortgulliet.adapter.ViajesAdapter
import com.example.resortgulliet.objetos.Destino
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_inicio.*
import java.util.*


class InicioFragment : Fragment() {
    lateinit var viajesList: ArrayList<Destino>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viajesList = ArrayList<Destino>()

        imageButtonBuscar.setOnClickListener {
            editTextBuscadorPais.text.toString()
        }

        val baseDate = FirebaseDatabase.getInstance().reference.child("Destinos")

        recyclerPagos.layoutManager = LinearLayoutManager(context)

        recyclerPagos.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        val valueLise: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (recyclerPagos != null) {
                    val usuarioDado = FirebaseAuth.getInstance().currentUser!!.email
                    viajesList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val messagesRuser = dataSnapshot.getValue(Destino::class.java)
                        if (messagesRuser!!.usuario != usuarioDado){
                            if (editTextBuscadorPais.text.toString().isEmpty()) {
                                viajesList.add(messagesRuser!!)
                            } else {
                                if (editTextBuscadorPais.text.toString().toUpperCase().equals(messagesRuser.pais!!.toUpperCase())) {
                                    viajesList.add(messagesRuser!!)
                                }
                            }
                        }
                    }
                    val adapter = ViajesAdapter(viajesList)
                    recyclerPagos.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        baseDate.addValueEventListener(valueLise)
    }

}