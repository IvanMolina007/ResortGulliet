package com.example.resortgulliet.objetos
//1 -> Euro
//2 -> Corona Checa
//3 -> Libra Esterlina
//4 -> Zloty (Polonia)
//5 -> Rublo Ruso
//6 -> Franco Suizo
//7 -> Florin Hungaro
class Usuario {
    var id: String? = null
    var nombre: String? = null
    var nacionalidad: String? = null
    var saldo: Double? = null
    var correo: String? = null
    var moneda: Int? = null

    constructor(){}

    constructor(id: String, nombre: String, nacionalidad: String, saldo: Double, correo: String, moneda: Int){
        this.id = id
        this.nombre = nombre
        this.nacionalidad = nacionalidad
        this.saldo = saldo
        this.correo = correo
        this.moneda = moneda
    }
}
