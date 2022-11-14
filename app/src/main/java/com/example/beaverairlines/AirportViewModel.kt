package com.example.beaverairlines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.api.AirportRepository
import com.example.beaverairlines.data.model.Airport
import com.example.beaverairlines.data.remote.SearchAirportApi
import kotlinx.coroutines.launch

const val Tag= "AirportViewModel"
enum class ApiStatus { LOADING, DONE, ERROR }

class AirportViewModel: androidx.lifecycle.ViewModel() {

    val airportRepository = AirportRepository()

    val airportList = airportRepository.airports
    val downloadMsg = airportRepository.downloadMsg
    //val airports: LiveData<List<Airport>> = airportRepository.airports


    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadDownloadMsg() {
        viewModelScope.launch {
            _loading.value = true

            airportRepository.downloadNotification()

            _loading.value = false
        }
    }

    private val _results = MutableLiveData<ApiStatus>()
    val results: LiveData<ApiStatus>
        get() = _results



    fun searchAirports(term: String){
        viewModelScope.launch {
            try {
                _results.value = ApiStatus.LOADING
                airportRepository.loadAirports(term)
                _results.value = ApiStatus.DONE
            } catch (e: Exception){
                Log.e(Tag,"An Error occurred whilst loading your search request...")
                _results.value = ApiStatus.ERROR
            }

        }
    }
}