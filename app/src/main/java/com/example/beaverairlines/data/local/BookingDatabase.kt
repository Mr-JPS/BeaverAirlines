package com.example.beaverairlines.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.beaverairlines.data.model.Booking

@Database(entities = [Booking::class], version = 1)
abstract class BookingDatabase: RoomDatabase() {

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