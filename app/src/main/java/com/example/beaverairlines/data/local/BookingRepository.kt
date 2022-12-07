package com.example.beaverairlines.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.beaverairlines.data.model.Booking


//THIS REPO HANDLES THE STORAGE OF BOOKINGS:

const val TAG = "BookingRepository"
class BookingRepository(private val  database: BookingDatabase) {

    //IS USED BY AN OBSERVER IN TRIPS FRAGMENT TO SHOW RECENT BOOKINGS:
    val bookingList: LiveData<List<Booking>> = database.bookingDatabaseDao.getAll()


    private val _currentBooking = MutableLiveData<Booking>()
    val currentBooking: LiveData<Booking>
        get() = _currentBooking


    val nextCheckin =  MutableLiveData<Booking>()



    //IS USED TO DETERMINATE THE CHECK AVL CHECKIN:
     suspend fun getNextCheckin(){
        try {
        nextCheckin.postValue(database.bookingDatabaseDao.getNextCheckin())
        } catch (e: Exception) {
            Log.e(TAG, "Error writing data in database: $e")
        }
    }


    //INSERTING BOOKINGS:
    suspend fun insert(booking: Booking) {
        try {
            database.bookingDatabaseDao.insert(booking)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing data in database: $e")
        }
    }


    //GETTING BOOKINGS:
    fun getBooking(bookingNbr: String) {
        try {
            _currentBooking.postValue(database.bookingDatabaseDao.getById(bookingNbr))
        } catch (e: Exception) {
            Log.e(TAG, "Error finding $bookingNbr in database: $e")
        }
    }

    //UPDATE BOOKINGS:
    suspend fun updateBooking(booking: Booking) {
        try {
            database.bookingDatabaseDao.update(booking)
        } catch (e: Exception) {
            Log.e(TAG, "Error updating guest in database: $e")
        }
    }


}