package com.example.beaverairlines

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.data.local.BookingRepository
import com.example.beaverairlines.data.local.getDatabase
import com.example.beaverairlines.data.model.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel (application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = BookingRepository(database)

    val bookingList = repository.bookingList
    val currentBooking = repository.currentBooking
    var reservationNbr: String = ""

    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete

    fun insertBooking(booking: Booking) {
        viewModelScope.launch {
            repository.insert(booking)
            _complete.value = true
        }
    }

    fun getBooking(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getBooking(id)
        }
    }

    fun updateBooking(booking: Booking) {
        viewModelScope.launch {
            repository.updateBooking(booking)
            _complete.value = true
        }
    }

    fun deleteBooking(id: String) {
        viewModelScope.launch {
            repository.deleteBooking(id)
            _complete.value = true
        }
    }

    // wird nach Beendigung der Navigation wieder auf false zurückgesetzt
    fun unsetComplete() {
        _complete.value = false
    }
}