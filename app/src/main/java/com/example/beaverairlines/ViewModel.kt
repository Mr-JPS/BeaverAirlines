package com.example.beaverairlines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.api.Repository
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.Iata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//MAIN VIEWMODEL

class ViewModel(application: Application) : AndroidViewModel(application) {

    //REPOS
    private val repository: Repository = Repository()
    val offers = repository.offersParsed
    val status = repository.status
    val started = repository.started

    //IATA LIST
    val iata = readCsv(R.raw.iata)

    //FLIGHT SEARCH DEDICATED VALUES
    var depIata: String = ""
    var depCity: String = ""
    var ariIata: String = ""
    var ariCity: String = ""
    var depIata2: String = ""
    var depCity2: String = ""
    var ariIata2: String = ""
    var ariCity2: String = ""
    var departureDATE: String = ""
    var arrivalDATE: String  = ""
    var adultPassenger: String = ""

    //FLIGHT OFFER DEDICATED VALUES:
    var flight1: FlightOffer? = null
    var bookingNbr1: String = ""
    var flight2: FlightOffer? = null
    var bookingNbr2: String = ""

    //VALUES FOR INTERACTION BETWEEN FRAGMENTS:
    val paymentCompleted : MutableLiveData<Boolean> = MutableLiveData(false)
    val clubCardClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val dBFlightSearchClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val ciCardClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val passportSaved: MutableLiveData<Boolean> = MutableLiveData(false)



    //METHOD TO READ IATA FROM CSV
    fun readCsv(csv: Int): ArrayList<Iata> {
        val reader = getApplication<Application>().resources.openRawResource(csv).bufferedReader()
        val header = reader.readLine()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (iata, name) = it.split(',', ignoreCase = false, limit = 2)
                Iata(iata.toString(), name.toString())
            }.toList() as ArrayList<Iata>
    }



    //METHOD TO REQUEST FLIGHTS
    fun getFlights(origin: String, destination: String, date: String, adults: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFlights(origin, destination, date, adults)
        }
    }


    //METHOD TO RESET
    fun resetAllValues(){
        repository.resetAllValues()
    }

}