package com.example.beaverairlines.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.model.Booking

@Dao
interface BoardingPassDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(boardingpass : FinalBoardingPass)

    @Update
    suspend fun update(boardingpass : FinalBoardingPass)

    @Query("SELECT * FROM FinalBoardingPass")
    fun getAll(): LiveData<List<FinalBoardingPass>>

    @Query("SELECT * FROM FinalBoardingPass WHERE id = :id")
    fun getById(id: Int): FinalBoardingPass



}