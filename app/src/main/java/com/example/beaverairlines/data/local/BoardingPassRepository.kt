package com.example.beaverairlines.data.local

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.model.Booking

//THIS REPO HANDLES THE STORAGE OF FINAL BOARDING PASSES:

class BoardingPassRepository (private val  database: BookingDatabase) {


    //IS USES BY AN OBSERVER IN CHECKIN FRAGMENT TO SHOW THE FINAL BOARDING PASS
    private val _finalBP = MutableLiveData<FinalBoardingPass?>()
    val finalBP: MutableLiveData<FinalBoardingPass?>
        get() = _finalBP


    //A LIST OF ALL ISSUED BOARDING PASSES USED BY THE ADAPTER IN CHECKIN FRAGMENT:
    val bPList: LiveData<List<FinalBoardingPass>> = database.boardingPassDao.getAll()



    suspend fun insert(boardingpass: FinalBoardingPass) {
        try {
            database.boardingPassDao.insert(boardingpass)
        } catch (e: Exception) {
            Log.e(TAG, "Error writing data in database: $e")
        }
    }

    fun getBoardingpass(id: Int) {
        try {
            _finalBP.postValue(database.boardingPassDao.getById(id))
        } catch (e: Exception) {
            Log.e(TAG, "Error finding in database: $e")
        }
    }

    fun resetBP(){
        _finalBP.value = null
    }



}