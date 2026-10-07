package com.example.data.repository

import com.example.data.local.RatingDao
import com.example.data.local.RatingEntity
import com.example.data.model.SarpanchReport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RatingRepository(private val ratingDao: RatingDao) {

    val allRatings: Flow<List<SarpanchReport>> = ratingDao.getAllRatings().map { entities ->
        entities.map { it.toDomainModel() }
    }

    val totalCount: Flow<Int> = ratingDao.getRatingCount()

    val allPanchayats: Flow<List<String>> = ratingDao.getAllPanchayats()

    fun getRatingsByPanchayat(panchayatName: String): Flow<List<SarpanchReport>> {
        return ratingDao.getRatingsByPanchayat(panchayatName).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    suspend fun insertRating(report: SarpanchReport): Long {
        val entity = RatingEntity.fromDomainModel(report)
        return ratingDao.insertRating(entity)
    }

    suspend fun getRatingById(id: Long): SarpanchReport? {
        return ratingDao.getRatingById(id)?.toDomainModel()
    }

    suspend fun deleteRating(id: Long) {
        ratingDao.deleteById(id)
    }
}
