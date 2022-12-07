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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//VIEW MODEL FOR BOOKING RELATED ACTIVITIES:

class BookingViewModel (application: Application): AndroidViewModel(application) {

    //REPOS
    private val database = getDatabase(application)
    private val repository = BookingRepository(database)
    private val boardingPassRepository: BoardingPassRepository = BoardingPassRepository(database)


    //LIST OF ISSUED BOARDING PASSES
    val finalBoardingPass = boardingPassRepository.finalBP
    val boardingpassList = boardingPassRepository.bPList


    //VALUE TO CHECK IF A NEW CHECKIN IS AVL
    val nextCheckin = repository.nextCheckin


    //LISTS FOR BOOKINGS:
    val bookingList = repository.bookingList
    val currentBooking = repository.currentBooking


    //VALUES TO PASSED BETWEEN DIFFERENT EVENTS:
    var reservationNbr: String = ""
    //var isBoardingPassIssued: Boolean = false
    var passFirstname: String = ""
    var passSurname: String = ""
    var destinationIata: String = ""
    var boardingtime: String = ""
    var gate: String = ""
    var assignedSeat: String = ""


    //VALUES TO BE OBSERVED FOR BOARDING PASSES:
    private val _isBoardingPassIssued = MutableLiveData<Boolean>(false)
    val isBoardingPassIssued: LiveData<Boolean>
        get() = _isBoardingPassIssued


    //VALUES TO BE OBSERVED IS STATUS IS COMPLETED:
    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete


    //VALUES TO BE OBSERVED FOR DECLARING FINAL BOARDING PASS:
    private val _finalBP = MutableLiveData<FinalBoardingPass>()
    val finalBP: LiveData<FinalBoardingPass>
        get() = _finalBP


    //VALUES TO BE OBSERVED IF BOARDING PASS PREVIEW WAS CLICKED:
    private val _wasBPpreviewClicked = MutableLiveData<Boolean>()
    val wasBPpreviewClicked: LiveData<Boolean>
        get() = _wasBPpreviewClicked



    //METHOD TO LOAD BOARDING PASSES FROM REPO. TO BE USED IN CHECKIN FRAGMENT:
    fun loadBp(id: Int){
        boardingPassRepository.getBoardingpass(id)
    }



    //METHOD TO SVE BOARDING PASSES TO DATABASE DAO:
    fun saveIssuedBoardingPass(boardingPass: FinalBoardingPass){
        viewModelScope.launch(Dispatchers.IO) {

              boardingPassRepository.insert(boardingPass)
//            boardingPassRepository.getFinalBP(boardingPass)
//            _finalBP.value = boardingPass
              _isBoardingPassIssued.postValue(true)
              boardingPassRepository.finalBP.postValue(boardingPass)
        }
    }



    //METHOD TO INSERT BOOKING TO DATABASE:
    fun insertBooking(booking: Booking) {
        viewModelScope.launch {

            repository.insert(booking)
            _complete.value = true
        }
    }



    //METHOD TO GET NEXT AVL CHECKIN:
    fun getNextCheckin(){
        viewModelScope.launch(Dispatchers.IO) {

            delay(500)
            repository.getNextCheckin()
        }
    }



    //METHOD TO UPDATE BOOKING IN DATABASE DAO:
    fun updateBooking(booking: Booking) {
        viewModelScope.launch {

            repository.updateBooking(booking)
            _complete.value = true
        }
    }



    //METHOD TO RESET BOARDING PASS:
    fun resetBP() {
        boardingPassRepository.resetBP()
    }





//    fun setBoardingPass(id: String) {
//        finalBoardingPass
//    }

    fun unsetComplete() {
        _complete.value = false
    }
}