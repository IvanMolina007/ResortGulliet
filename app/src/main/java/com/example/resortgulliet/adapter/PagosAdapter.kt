package com.example.resortgulliet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Factura
import com.example.resortgulliet.objetos.Pago

import kotlinx.android.synthetic.main.facturas_row.view.*
import kotlinx.android.synthetic.main.facturas_row.view.textViewPagoDestino
import kotlinx.android.synthetic.main.facturas_row.view.textViewPagoIdUsuario
import kotlinx.android.synthetic.main.facturas_row.view.textViewPagoPrecio
import kotlinx.android.synthetic.main.pagos_row.view.*

class PagosAdapter(private val pagosList: ArrayList<Pago>): RecyclerView.Adapter<PagosAdapter.PagosHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagosHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagosHolder(layoutInflater.inflate(R.layout.pagos_row, parent, false))
    }

    override fun onBindViewHolder(holder: PagosHolder, position: Int) {
        holder.render(pagosList[position])
    }

    override fun getItemCount(): Int {
        return pagosList.size
    }

    class PagosHolder(val view: View):RecyclerView.ViewHolder(view) {
        var boolean: Boolean = true
        fun render(pago: Pago) {
            view.textViewPagoIdUsuario.text = pago.idUser
            view.textViewPagoDestino.text = "Destino: " + pago.nombreDestino
            view.textViewPagoPrecio.text = "Precio: " + pago.pagoDestino
            view.textViewPagoNombreUsuario.text = "Lo compro el usuario: " + pago.nombreComprador


        }
    }
}