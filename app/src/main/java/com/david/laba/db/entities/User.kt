package com.david.laba.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("pk")
    val uid: Long,

    val userName: String = "",
    val userPhoneNumber: String = "",
    val userAvatar: Int? = null,
    val token: String? = null,

    )