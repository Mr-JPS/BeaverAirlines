package com.example.beaverairlines.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//DATA CLASS FOR FINAL BOARDING PASSES IN CHECKIN PROCEDURE:

@Entity
data class FinalBoardingPass(

    val passFirstname: String,
    val passSurname: String,
    val destinationIata: String,
    val boardingtime: String,
    val gate: String,
    val assignedSeat: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)