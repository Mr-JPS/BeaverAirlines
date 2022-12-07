package com.example.beaverairlines.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beaverairlines.data.model.Booking

//THIS DATABASE HANDLES THE STORAGE OF BOOKINGS:

@Dao
interface BookingDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking : Booking)

    @Update
    suspend fun update(booking : Booking)

    @Query("SELECT * FROM Booking")
    fun getAll(): LiveData<List<Booking>>

    @Query("SELECT * FROM Booking WHERE reservationNbr = :id")
    fun getById(id: String): Booking

    @Query("SELECT * FROM Booking WHERE isCheckedin = 0 LIMIT 1")
    fun getNextCheckin(): Booking




}