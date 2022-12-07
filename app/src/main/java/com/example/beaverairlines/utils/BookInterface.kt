package com.example.beaverairlines.utils

import com.example.beaverairlines.data.FlightOffer

//METHODS TO MAKE A COMMUNICATION BETWEEN DIFFERENT OBJECTS:

interface BookInterface {
    fun openFlight()
    fun openReturnFlight(flight: FlightOffer, bookingNbr: String)
    fun openPayment(depIata: String, ariIata: String, returnFlight: FlightOffer, returnBookingNbr: String)


}