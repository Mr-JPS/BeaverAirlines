package com.example.beaverairlines.data

data class Iata(
    val iata: String,
    val name: String


){
    override fun toString(): String {
        return "$name - $iata"
    }
}