package com.david.laba.net.service

import com.david.laba.db.entities.User
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserService {
    @GET("/login/phone")
    suspend fun sendPhone(@Query("phone_number") phoneNum: String)

    @GET("/login/code")
    suspend fun sendCode(@Query("phone_number") phoneNum: String, @Query("code") code: String): User

    @GET("/login/username")
    suspend fun sendUserName(@Header("token") token: String, @Query("user_name") userName: String)
}