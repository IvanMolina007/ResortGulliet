package com.example.resortgulliet.objetos

class Destino {
    var nombreDes: String? = null
    var pais: String? = null
    var gama: Int? = null
    var precio: Double? = null
    var imagenDes: String? = null
    var usuario: String? = null

    constructor(){}

    constructor(nombreDes: String, pais: String, gama: Int, precio: Double, imagenDes: String, usuario: String){
        this.nombreDes = nombreDes
        this.pais = pais
        this.gama = gama
        this.precio = precio
        this.imagenDes = imagenDes
        this.usuario = usuario
    }



}