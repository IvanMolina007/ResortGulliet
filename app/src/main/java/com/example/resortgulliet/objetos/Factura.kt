package com.example.resortgulliet.objetos
class Factura {
    var idUser: String? = null
    var precioDestino: String? = null
    var nombreDestino: String? = null

    constructor(){}

    constructor(idUser: String, precioDestino: String, nombreDestino: String){
        this.idUser = idUser
        this.precioDestino = precioDestino
        this.nombreDestino = nombreDestino
    }
}
