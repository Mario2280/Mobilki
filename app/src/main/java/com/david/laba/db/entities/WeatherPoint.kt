package com.david.laba.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherPoint(
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,
    var title: String = "",
    var longitude: Double = 0.0,
    var latitude: Double = 0.0,
    var imgUrl: String? = null,
    var weatherType: String? = null
/*    @Embedded
    var offers: List<Offer> = ArrayList()*/
)