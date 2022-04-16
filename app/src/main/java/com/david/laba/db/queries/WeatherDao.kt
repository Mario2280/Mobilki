package com.david.laba.db.queries

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.david.laba.db.entities.WeatherPoint

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherpoint")
    fun getAll(): List<WeatherPoint>

    @Insert
    fun insert(weatherPoint: WeatherPoint): Long

    @Query("DELETE FROM weatherpoint")
    fun deleteAll()
}