package com.example.beaverairlines.utils

import com.example.beaverairlines.data.FlightOffer

interface BookInterface {
    fun openFlight ()
    fun openReturnFlight (flight: FlightOffer, bookingNbr: String)
}