package com.example.resortgulliet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.resortgulliet.R
import com.example.resortgulliet.objetos.Factura

import kotlinx.android.synthetic.main.facturas_row.view.*

class FacturasAdapter(private val facturasList: ArrayList<Factura>): RecyclerView.Adapter<FacturasAdapter.FacturasHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FacturasHolder(layoutInflater.inflate(R.layout.facturas_row, parent, false))
    }

    override fun onBindViewHolder(holder: FacturasHolder, position: Int) {
        holder.render(facturasList[position])
    }

    override fun getItemCount(): Int {
        return facturasList.size
    }

    class FacturasHolder(val view: View):RecyclerView.ViewHolder(view) {
        var boolean: Boolean = true
        fun render(factura: Factura) {
            view.textViewPagoIdUsuario.text = factura.idUser
            view.textViewPagoDestino.text = "Destino: " + factura.nombreDestino
            view.textViewPagoPrecio.text = "Precio: " + factura.precioDestino


        }
    }
}