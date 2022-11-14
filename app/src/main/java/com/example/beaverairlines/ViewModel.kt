package com.example.beaverairlines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.api.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository()
    val offers = repository.offersParsed
    val status = repository.status
    val started = repository.started

    fun getFlights(origin: String, destination: String, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFlights(origin, destination, date)
        }
    }

    fun resetAllValues(){
        repository.resetAllValues()
    }

}