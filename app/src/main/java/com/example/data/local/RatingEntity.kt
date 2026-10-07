package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.DevelopmentCategory
import com.example.data.model.SarpanchReport

@Entity(tableName = "sarpanch_ratings")
data class RatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val villageName: String,
    val panchayatName: String,
    val sarpanchName: String,
    val roadRating: Int,
    val drainageRating: Int,
    val electricityRating: Int,
    val cleanlinessRating: Int,
    val waterRating: Int,
    val educationRating: Int,
    val healthRating: Int,
    val streetLightRating: Int,
    val govSchemesRating: Int,
    val overallDevelopmentRating: Int,
    val overallScore: Double,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): SarpanchReport {
        val ratingsMap = mapOf(
            DevelopmentCategory.ROAD to roadRating,
            DevelopmentCategory.DRAINAGE to drainageRating,
            DevelopmentCategory.ELECTRICITY to electricityRating,
            DevelopmentCategory.CLEANLINESS to cleanlinessRating,
            DevelopmentCategory.DRINKING_WATER to waterRating,
            DevelopmentCategory.EDUCATION to educationRating,
            DevelopmentCategory.HEALTH to healthRating,
            DevelopmentCategory.STREET_LIGHT to streetLightRating,
            DevelopmentCategory.GOV_SCHEMES to govSchemesRating,
            DevelopmentCategory.OVERALL_DEVELOPMENT to overallDevelopmentRating
        )
        return SarpanchReport(
            id = id,
            villageName = villageName,
            panchayatName = panchayatName,
            sarpanchName = sarpanchName,
            categoryRatings = ratingsMap,
            comment = comment,
            timestamp = timestamp
        )
    }

    companion object {
        fun fromDomainModel(report: SarpanchReport): RatingEntity {
            return RatingEntity(
                id = report.id,
                villageName = report.villageName,
                panchayatName = report.panchayatName,
                sarpanchName = report.sarpanchName,
                roadRating = report.categoryRatings[DevelopmentCategory.ROAD] ?: 5,
                drainageRating = report.categoryRatings[DevelopmentCategory.DRAINAGE] ?: 5,
                electricityRating = report.categoryRatings[DevelopmentCategory.ELECTRICITY] ?: 5,
                cleanlinessRating = report.categoryRatings[DevelopmentCategory.CLEANLINESS] ?: 5,
                waterRating = report.categoryRatings[DevelopmentCategory.DRINKING_WATER] ?: 5,
                educationRating = report.categoryRatings[DevelopmentCategory.EDUCATION] ?: 5,
                healthRating = report.categoryRatings[DevelopmentCategory.HEALTH] ?: 5,
                streetLightRating = report.categoryRatings[DevelopmentCategory.STREET_LIGHT] ?: 5,
                govSchemesRating = report.categoryRatings[DevelopmentCategory.GOV_SCHEMES] ?: 5,
                overallDevelopmentRating = report.categoryRatings[DevelopmentCategory.OVERALL_DEVELOPMENT] ?: 5,
                overallScore = report.overallRating,
                comment = report.comment,
                timestamp = report.timestamp
            )
        }
    }
}
