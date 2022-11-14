package com.example.beaverairlines.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beaverairlines.data.model.Airport
import com.example.beaverairlines.data.remote.SearchAirportApi
import kotlinx.coroutines.delay

class AirportRepository {

    private val _airports = MutableLiveData<List<Airport>>()
    val airports : LiveData<List<Airport>>
        get() = _airports

    private val _downloadMsg = MutableLiveData<String>()
    val downloadMsg: LiveData<String>
        get() = _downloadMsg

    suspend fun downloadNotification(){
        for (i in 0 until 100 step 5){
            _downloadMsg.value = "Loading: $i%"
            //delay(100)
        }

        _downloadMsg.value = "Load completed"

    }

    suspend fun loadAirports(term: String) {
        val response = SearchAirportApi.retrofitService.getResult(term)
        _airports.value = response.results
    }

}