package com.david.laba.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Offer(
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0,
    var name: String = "",
    var description: String = ""
)