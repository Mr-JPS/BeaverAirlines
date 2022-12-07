package com.example.beaverairlines.data

//DATA CLASS FOR FLIGHT OFFERS:

data class FlightOffer(
    val departureTime: String,
    val arrivalTime: String,
    val duration: String,
    val price: String
)