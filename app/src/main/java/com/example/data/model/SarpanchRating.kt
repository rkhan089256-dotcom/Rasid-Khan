package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WrongLocation
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The 10 primary development categories for 5-year Sarpanch evaluation.
 */
enum class DevelopmentCategory(
    val id: String,
    val hindiTitle: String,
    val englishTitle: String,
    val description: String
) {
    ROAD(
        id = "road",
        hindiTitle = "सड़क",
        englishTitle = "Roads & Connectivity",
        description = "पक्की सड़कें, मरम्मत और गांव का मुख्य मार्ग से जुड़ाव"
    ),
    DRAINAGE(
        id = "drainage",
        hindiTitle = "नाली",
        englishTitle = "Drainage System",
        description = "पक्की नालियां, पानी की सही निकासी और जल-भराव की रोकथाम"
    ),
    ELECTRICITY(
        id = "electricity",
        hindiTitle = "बिजली",
        englishTitle = "Electricity Supply",
        description = "बिजली की नियमित आपूर्ति, ट्रांसफॉर्मर और तारों का रखरखाव"
    ),
    CLEANLINESS(
        id = "cleanliness",
        hindiTitle = "साफ-सफाई",
        englishTitle = "Sanitation & Cleanliness",
        description = "कचरा प्रबंधन, सार्वजनिक स्थानों की स्वच्छता और नियमित झाड़ू"
    ),
    DRINKING_WATER(
        id = "water",
        hindiTitle = "पीने का पानी",
        englishTitle = "Drinking Water",
        description = "नल-जल योजना, हैंडपंप मरम्मत और शुद्ध पेयजल की उपलब्धता"
    ),
    EDUCATION(
        id = "education",
        hindiTitle = "शिक्षा",
        englishTitle = "Education & Schools",
        description = "प्राथमिक/माध्यमिक स्कूल, शिक्षकों की उपस्थिति और सुविधाएं"
    ),
    HEALTH(
        id = "health",
        hindiTitle = "स्वास्थ्य",
        englishTitle = "Health & Dispensaries",
        description = "स्वास्थ्य उप-केंद्र, टीकाकरण, दवाइयों की उपलब्धता और आशा सहयोग"
    ),
    STREET_LIGHT(
        id = "street_light",
        hindiTitle = "स्ट्रीट लाइट",
        englishTitle = "Street Lighting",
        description = "चौराहों और गलियों में सोलर/एलईडी स्ट्रीट लाइट की व्यवस्था"
    ),
    GOV_SCHEMES(
        id = "gov_schemes",
        hindiTitle = "सरकारी योजनाओं का लाभ",
        englishTitle = "Govt Schemes Benefit",
        description = "पीएम आवास, राशन, किसान सम्मान निधि, पेंशन वितरण में पारदर्शिता"
    ),
    OVERALL_DEVELOPMENT(
        id = "overall_dev",
        hindiTitle = "गांव का overall विकास",
        englishTitle = "Overall Village Development",
        description = "भाईचारा, पंचायत भवन, खेल मैदान, रोजगार और गांव का सर्वांगीण विकास"
    );

    fun getIcon(): ImageVector {
        return when (this) {
            ROAD -> Icons.Default.WrongLocation
            DRAINAGE -> Icons.Default.Build
            ELECTRICITY -> Icons.Default.ElectricBolt
            CLEANLINESS -> Icons.Default.CleaningServices
            DRINKING_WATER -> Icons.Default.WaterDrop
            EDUCATION -> Icons.Default.School
            HEALTH -> Icons.Default.LocalHospital
            STREET_LIGHT -> Icons.Default.Lightbulb
            GOV_SCHEMES -> Icons.Default.Policy
            OVERALL_DEVELOPMENT -> Icons.Default.Agriculture
        }
    }
}

/**
 * Holds rating breakdown and metadata for a submission.
 */
data class SarpanchReport(
    val id: Long = 0,
    val villageName: String,
    val panchayatName: String,
    val sarpanchName: String,
    val categoryRatings: Map<DevelopmentCategory, Int>,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    val overallRating: Double
        get() = if (categoryRatings.isEmpty()) 0.0 else {
            val sum = categoryRatings.values.sum()
            Math.round((sum.toDouble() / categoryRatings.size.toDouble()) * 10.0) / 10.0
        }

    fun getStarVisual(score: Int): String {
        val safeScore = score.coerceIn(1, 10)
        val filled = "⭐".repeat(safeScore)
        val empty = "☆".repeat(10 - safeScore)
        return "$filled$empty $safeScore/10"
    }

    val gradeTitle: String
        get() = when {
            overallRating >= 8.5 -> "उत्कृष्ट (शानदार प्रदर्शन)"
            overallRating >= 7.0 -> "बहुत अच्छा (संतोषजनक कार्य)"
            overallRating >= 5.0 -> "औसत (कुछ विकास हुआ)"
            overallRating >= 3.0 -> "कमजोर (सुधार की बहुत आवश्यकता)"
            else -> "असंतोषजनक (विकास कार्य ठप)"
        }
}
