package com.example.resortgulliet.objetos
class Pago {
    var idUser: String? = null
    var pagoDestino: String? = null
    var nombreDestino: String? = null
    var nombreComprador: String? = null

    constructor(){}

    constructor(idUser: String, pagoDestino: String, nombreDestino: String, nombreComprador: String){
        this.idUser = idUser
        this.pagoDestino = pagoDestino
        this.nombreDestino = nombreDestino
        this.nombreComprador = nombreComprador
    }
}
