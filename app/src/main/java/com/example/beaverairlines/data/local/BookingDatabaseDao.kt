package com.example.beaverairlines.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beaverairlines.data.model.Booking

@Dao
interface BookingDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking : Booking)

    @Update
    suspend fun update(booking : Booking)

    @Query("SELECT * FROM Booking")
    fun getAll(): LiveData<List<Booking>>

    @Query("SELECT * FROM Booking WHERE ticketReservationNbr = :id")
    fun getById(id: String): Booking

    @Query("DELETE FROM Booking WHERE ticketReservationNbr = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE from Booking")
    suspend fun deleteAll()

}