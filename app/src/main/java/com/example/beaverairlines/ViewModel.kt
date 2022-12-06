package com.example.beaverairlines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.api.Repository
import com.example.beaverairlines.data.FlightOffer
import com.example.beaverairlines.data.Iata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository()
    val offers = repository.offersParsed
    val status = repository.status
    val started = repository.started

    val iata = readCsv(R.raw.iata)

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

    var flight1: FlightOffer? = null
    var bookingNbr1: String = ""


    var flight2: FlightOffer? = null
    var bookingNbr2: String = ""

    val paymentCompleted : MutableLiveData<Boolean> = MutableLiveData(false)
    val clubCardClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val dBFlightSearchClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val ciCardClicked : MutableLiveData<Boolean> = MutableLiveData(false)
    val passportSaved: MutableLiveData<Boolean> = MutableLiveData(false)



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



    fun getFlights(origin: String, destination: String, date: String, adults: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFlights(origin, destination, date, adults)
        }
    }

    fun resetAllValues(){
        repository.resetAllValues()
    }

}