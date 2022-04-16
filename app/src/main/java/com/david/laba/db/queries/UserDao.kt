package com.david.laba.db.queries

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.david.laba.db.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    fun obtain(): User

    @Insert
    fun insert(userInfo: User)

    @Query("DELETE FROM user")
    fun delete()
}