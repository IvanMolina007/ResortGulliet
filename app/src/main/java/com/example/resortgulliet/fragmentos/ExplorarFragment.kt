package com.example.resortgulliet.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resortgulliet.R
import com.example.resortgulliet.adapter.FacturasAdapter
import com.example.resortgulliet.adapter.PagosAdapter
import com.example.resortgulliet.objetos.Factura
import com.example.resortgulliet.objetos.Pago
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.fragment_usuario.*
import java.util.ArrayList

class ExplorarFragment : Fragment() {
    lateinit var pagosList: ArrayList<Pago>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explorar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagosList = ArrayList<Pago>()
        val baseDatee = FirebaseDatabase.getInstance().reference.child("Pagos")

        recyclerPagos.layoutManager = LinearLayoutManager(context)

        recyclerPagos.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        val valueLise: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val buser = FirebaseAuth.getInstance().currentUser!!.email
                if (recyclerPagos != null) {
                    pagosList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val messagesRuser = dataSnapshot.getValue(Pago::class.java)
                        if (messagesRuser!!.nombreComprador.equals(buser)) {
                            pagosList.add(messagesRuser!!)
                        }
                    }
                    val adapter = PagosAdapter(pagosList)
                    recyclerPagos.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        baseDatee.addValueEventListener(valueLise)
    }

}