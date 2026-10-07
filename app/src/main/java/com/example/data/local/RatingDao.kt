package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {
    @Query("SELECT * FROM sarpanch_ratings ORDER BY timestamp DESC")
    fun getAllRatings(): Flow<List<RatingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: RatingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ratings: List<RatingEntity>)

    @Query("SELECT * FROM sarpanch_ratings WHERE id = :id LIMIT 1")
    suspend fun getRatingById(id: Long): RatingEntity?

    @Query("SELECT * FROM sarpanch_ratings WHERE panchayatName = :panchayatName ORDER BY timestamp DESC")
    fun getRatingsByPanchayat(panchayatName: String): Flow<List<RatingEntity>>

    @Query("SELECT COUNT(*) FROM sarpanch_ratings")
    fun getRatingCount(): Flow<Int>

    @Query("SELECT DISTINCT panchayatName FROM sarpanch_ratings ORDER BY panchayatName ASC")
    fun getAllPanchayats(): Flow<List<String>>

    @Query("DELETE FROM sarpanch_ratings WHERE id = :id")
    suspend fun deleteById(id: Long)
}
