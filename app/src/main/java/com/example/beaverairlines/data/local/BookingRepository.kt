package com.example.beaverairlines.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beaverairlines.data.model.Booking


const val TAG = "BookingRepository"
class BookingRepository(private val  database: BookingDatabase) {

    val bookingList: LiveData<List<Booking>> = database.bookingDatabaseDao.getAll()

    private val _currentBooking = MutableLiveData<Booking>()
    val currentBooking: LiveData<Booking>
        get() = _currentBooking

    suspend fun insert(booking: Booking) {
        try {
            database.bookingDatabaseDao.insert(booking)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing data in database: $e")
        }
    }

    fun getBooking(bookingNbr: String) {
        try {
            _currentBooking.postValue(database.bookingDatabaseDao.getById(bookingNbr))
        } catch (e: Exception) {
            Log.e(TAG, "Error finding $bookingNbr in database: $e")
        }
    }



    suspend fun updateBooking(booking: Booking) {
        try {
            database.bookingDatabaseDao.update(booking)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating guest in database: $e")
        }
    }


}