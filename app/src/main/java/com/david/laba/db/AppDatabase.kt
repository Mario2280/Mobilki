package com.david.laba.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.david.laba.db.entities.WeatherPoint
import com.david.laba.db.entities.Offer
import com.david.laba.db.entities.User
import com.david.laba.db.queries.WeatherDao
import com.david.laba.db.queries.OfferDao
import com.david.laba.db.queries.UserDao

@Database(
    entities = [WeatherPoint::class, Offer::class, User::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao
    abstract fun offerDao(): OfferDao
}