package com.example.beaverairlines.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beaverairlines.data.FinalBoardingPass
import com.example.beaverairlines.data.model.Booking

//THIS DATABASE HANDLES THE FUNCTINALITY OF FINAL BOARDING PASSES AND BOOKINGS:

@Database(entities = [Booking::class, FinalBoardingPass::class], version = 1)
abstract class BookingDatabase: RoomDatabase() {

    abstract val boardingPassDao : BoardingPassDatabaseDao
    abstract val bookingDatabaseDao : BookingDatabaseDao
}


private lateinit var INSTANCE: BookingDatabase


fun getDatabase (context: Context): BookingDatabase {

    synchronized(BookingDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                BookingDatabase::class.java,
                "booking_database"
            )
                .build()
        }
    }
    return INSTANCE
}