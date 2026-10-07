package com.example

import com.example.data.model.DevelopmentCategory
import com.example.data.model.SarpanchReport
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testSarpanchOverallRatingCalculation() {
        val ratings = mapOf(
            DevelopmentCategory.ROAD to 8,
            DevelopmentCategory.DRAINAGE to 7,
            DevelopmentCategory.ELECTRICITY to 9,
            DevelopmentCategory.CLEANLINESS to 6,
            DevelopmentCategory.DRINKING_WATER to 8,
            DevelopmentCategory.EDUCATION to 8,
            DevelopmentCategory.HEALTH to 7,
            DevelopmentCategory.STREET_LIGHT to 8,
            DevelopmentCategory.GOV_SCHEMES to 9,
            DevelopmentCategory.OVERALL_DEVELOPMENT to 8
        )
        val report = SarpanchReport(
            villageName = "रामपुर",
            panchayatName = "ग्राम पंचायत रामपुर",
            sarpanchName = "श्री रामेश्वर यादव",
            categoryRatings = ratings,
            comment = "अच्छा कार्य"
        )

        // Sum = 78, Average = 7.8
        assertEquals(7.8, report.overallRating, 0.01)
        val starVisual = report.getStarVisual(8)
        assertTrue(starVisual.contains("⭐⭐⭐⭐⭐⭐⭐⭐☆☆"))
        assertTrue(starVisual.contains("8/10"))
    }

    @Test
    fun testAllTenCategoriesPresent() {
        assertEquals(10, DevelopmentCategory.entries.size)
    }
}
