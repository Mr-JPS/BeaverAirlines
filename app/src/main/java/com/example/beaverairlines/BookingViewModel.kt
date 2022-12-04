package com.example.beaverairlines

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beaverairlines.api.Repository
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.local.BoardingPassRepository
import com.example.beaverairlines.data.local.BookingRepository
import com.example.beaverairlines.data.local.getDatabase
import com.example.beaverairlines.data.model.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel (application: Application): AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val repository = BookingRepository(database)

    private val boardingPassRepository: BoardingPassRepository = BoardingPassRepository(database)
    val finalBoardingPass = boardingPassRepository.finalBP
    val boardingpassList = boardingPassRepository.bPList

    val nextCheckin = repository.nextCheckin

    val bookingList = repository.bookingList
    val currentBooking = repository.currentBooking
    var reservationNbr: String = ""

    //var isBoardingPassIssued: Boolean = false
    var passFirstname: String = ""
    var passSurname: String = ""
    var destinationIata: String = ""
    var boardingtime: String = ""
    var gate: String = ""
    var assignedSeat: String = ""

    private val _isBoardingPassIssued = MutableLiveData<Boolean>()
    val isBoardingPassIssued: LiveData<Boolean>
        get() = _isBoardingPassIssued


    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete


    private val _finalBP = MutableLiveData<FinalBoardingPass>()
    val finalBP: LiveData<FinalBoardingPass>
        get() = _finalBP


    private val _wasBPpreviewClicked = MutableLiveData<Boolean>()
    val wasBPpreviewClicked: LiveData<Boolean>
        get() = _wasBPpreviewClicked



    fun loadBp(id: Int){
        boardingPassRepository.getBoardingpass(id)
    }

    fun saveIssuedBoardingPass(boardingPass: FinalBoardingPass){
        viewModelScope.launch(Dispatchers.IO) {
            boardingPassRepository.finalBP.postValue(boardingPass)
            boardingPassRepository.insert(boardingPass)
//            boardingPassRepository.getFinalBP(boardingPass)
            //_finalBP.value = boardingPass
            _isBoardingPassIssued.value = true
        }
    }

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

    fun getNextCheckin(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNextCheckin()
        }
    }
    fun updateBooking(booking: Booking) {
        viewModelScope.launch {
            repository.updateBooking(booking)
            _complete.value = true
        }
    }



    // wird nach Beendigung der Navigation wieder auf false zurückgesetzt
    fun unsetComplete() {
        _complete.value = false
    }
}