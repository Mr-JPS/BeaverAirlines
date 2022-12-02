package com.example.beaverairlines.data

data class FinalBoardingPass(
    val passFirstname: String,
    val passSurname: String,
    val destinationIata: String,
    val boardingtime: String,
    val gate: String,
    val assignedSeat: String
)