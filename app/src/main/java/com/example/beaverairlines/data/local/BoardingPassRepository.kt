package com.example.beaverairlines.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.beaverairlines.data.FinalBoardingPass



class BoardingPassRepository () {

    private val _finalBP = MutableLiveData<FinalBoardingPass>()
    val finalBP: LiveData<FinalBoardingPass>
        get() = _finalBP


    fun getFinalBP(boardingPass: FinalBoardingPass){
        _finalBP.postValue(boardingPass)
    }



}