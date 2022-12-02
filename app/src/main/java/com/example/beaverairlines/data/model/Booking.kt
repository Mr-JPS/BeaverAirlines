package com.example.beaverairlines.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Booking(

    @PrimaryKey
    val reservationNbr: String,


    val flight1_flightNbr: String,
    val flight1_departDate: String,
    val flight1_departCity: String,
    val flight1_departIATA: String,
    val flight1_ariDate: String,
    val flight1_ariCity: String,
    val flight1_ariIATA: String,
    val flight1_takeoffTime: String,
    val flight1_touchdownTime: String,
    val flight1_duration: String,

    val flight1_passFirstname: String,
    val flight1_passSurname: String,
    val flight1_cabinclass: String,
    val flight1_price: String,



    val flight2_flightNbr: String,
    val flight2_departDate: String,
    val flight2_departCity: String,
    val flight2_departIATA: String,
    val flight2_ariDate: String,
    val flight2_ariCity: String,
    val flight2_ariIATA: String,
    val flight2_takeoffTime: String,
    val flight2_touchdownTime: String,
    val flight2_duration: String,

    val flight2_passFirstname: String,
    val flight2_passSurname: String,
    val flight2_cabinclass: String,
    val flight2_price: String,

    var isCheckedin : Boolean = false
)
