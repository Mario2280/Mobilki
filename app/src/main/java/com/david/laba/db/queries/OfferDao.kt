package com.david.laba.db.queries

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.david.laba.db.entities.Offer
import io.reactivex.rxjava3.core.Completable

@Dao
interface OfferDao {
    @Insert
    fun insert(offer: Offer): Completable

    @Query("DELETE FROM offer")
    fun deleteAll(): Completable
}