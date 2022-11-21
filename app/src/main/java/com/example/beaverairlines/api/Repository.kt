package com.example.beaverairlines.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.amadeus.Amadeus
import com.amadeus.Params
import com.amadeus.resources.FlightOfferSearch
import com.example.beaverairlines.data.FlightOffer

class Repository() {

    private val amadeus: Amadeus = Amadeus
        .builder("tUPGrbmz5jnpKGmjCREKHejJs6QhyzJF", "IoXiiCPJ4FdSoePy")
        .build()

    private val _offersParsed = MutableLiveData<MutableList<FlightOffer>>()
    val offersParsed: LiveData<MutableList<FlightOffer>>
        get() = _offersParsed

    private val _status = MutableLiveData<Boolean>(false)
    val status: LiveData<Boolean>
        get() = _status

    private val _started = MutableLiveData<Boolean>(false)
    val started: LiveData<Boolean>
        get() = _started

    suspend fun getFlights(origin: String, destination: String, date: String, adults: Int) {
        _started.postValue(true)
        val flightOffersSearches =
            amadeus.shopping.flightOffersSearch[Params
                .with("originLocationCode", origin)
                .and("destinationLocationCode", destination)
                .and("departureDate", date)
                .and("adults", adults)
                .and("max", 10)]

        parseFlightOffers(flightOffersSearches.toList())
        _status.postValue(true)
    }

    private fun parseFlightOffers(offersRaw: List<FlightOfferSearch>){
        val flightOffersParsed: MutableList<FlightOffer> = mutableListOf()

        for (fos in offersRaw) {
            val offer = FlightOffer(
                fos.itineraries[0].segments[0].departure.at.takeLast(8),
                fos.itineraries[0].segments[0].arrival.at.takeLast(8),
                fos.itineraries[0].duration,
                fos.price.total
            )
            flightOffersParsed.add(offer)
        }

        _offersParsed.postValue(flightOffersParsed)
    }

    fun resetAllValues(){
        _status.value = false
        _started.value = false
    }
}