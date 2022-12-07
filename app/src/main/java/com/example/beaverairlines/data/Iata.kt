package com.example.beaverairlines.data

//DATA CLASS FOR IATA CODES:

data class Iata(
    val iata: String,
    val name: String
){
    override fun toString(): String {
        return "$name - $iata"
    }
}