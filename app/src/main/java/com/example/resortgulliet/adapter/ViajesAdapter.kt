package com.example.resortgulliet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Destino
import com.example.resortgulliet.objetos.Factura
import com.example.resortgulliet.objetos.Pago
import com.example.resortgulliet.objetos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_datos.*
import kotlinx.android.synthetic.main.activity_subir_nuevo_destino.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import kotlinx.android.synthetic.main.viaje_row.view.*

class ViajesAdapter(private val viajesList: ArrayList<Destino>): RecyclerView.Adapter<ViajesAdapter.ViajesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViajesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViajesHolder(layoutInflater.inflate(R.layout.viaje_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViajesHolder, position: Int) {
        holder.render(viajesList[position])
    }

    override fun getItemCount(): Int {
        return viajesList.size
    }

    class ViajesHolder(val view: View):RecyclerView.ViewHolder(view) {
        var boolean: Boolean = true
        fun render(viaje: Destino) {
            view.textViewViajeNombre.text = viaje.nombreDes
            view.textViewViajePais.text = "Pais: " + viaje.pais
            view.textViewViajeGama.text = gamaString(viaje.gama)
            view.textViewViajePrecio.text = precioString(viaje.precio)
            view.textViewViajeUsuario.text = viaje.usuario
            Picasso.get().load(viaje.imagenDes).into(view.imageView)
            view.setOnClickListener {
                val baseDate = FirebaseDatabase.getInstance().reference.child("usuarios")
                val baseUser = FirebaseAuth.getInstance().currentUser
                val valueLise: ValueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (boolean) {
                            for (dataSnapshot in snapshot.children) {
                                var messagesRuser = dataSnapshot.getValue(Usuario::class.java)
                                if (baseUser!!.uid == messagesRuser!!.id) {
                                    messagesRuser.saldo = messagesRuser.saldo!! - viaje.precio!!
                                    val mDatabase = FirebaseDatabase.getInstance().reference
                                    boolean = false
                                    mDatabase.child("usuarios").child(baseUser.uid).child("saldo")
                                        .setValue(messagesRuser.saldo)

                                    Toast.makeText(view.context, "Ahora su saldo es de " + messagesRuser.saldo + " Generando factura del billete...", Toast.LENGTH_LONG).show()
                                    val factura = Factura(baseUser.uid, viaje.precio.toString(), viaje.nombreDes!!)
                                    val mDatabasee2 = FirebaseDatabase.getInstance().reference
                                    mDatabasee2.child("Facturas").push().setValue(factura)

                                }

                                if (messagesRuser.correo == viaje.usuario) {
                                    messagesRuser.saldo = messagesRuser.saldo!! + viaje.precio!!
                                    val mDatabase = FirebaseDatabase.getInstance().reference
                                    boolean = false
                                    mDatabase.child("usuarios").child(messagesRuser.id!!).child("saldo")
                                        .setValue(messagesRuser.saldo)

                                    val pago = Pago(viaje.usuario!!, viaje.precio.toString(), viaje.nombreDes!!, messagesRuser.correo!!)
                                    val mDatabasee2 = FirebaseDatabase.getInstance().reference
                                    mDatabasee2.child("Pagos").push().setValue(pago)
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                }
                baseDate.addValueEventListener(valueLise)
            }
        }

        private fun gamaString(gama: Int?): String {
            return when (gama) {
                1 -> "Gama: Caro"
                2 -> "Gama: Medio"
                3 -> "Gama: Barato"
                else -> "Gama: No esta calificado"
            }
        }

        private fun precioString(precio: Double?): String {
            return precio.toString() + "€"
        }
    }
}