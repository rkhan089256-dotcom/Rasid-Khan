package com.example.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DevelopmentCategory
import com.example.data.model.SarpanchReport
import com.example.ui.theme.AccentGold
import com.example.ui.theme.CivicGreen
import com.example.ui.theme.CivicOrange
import com.example.ui.theme.CivicRed
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SecondarySaffron
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReportCardScreen(
    report: SarpanchReport,
    onRateAnother: () -> Unit,
    onViewDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val scoreColor = when {
        report.overallRating >= 8.0 -> CivicGreen
        report.overallRating >= 6.0 -> PrimaryNavy
        report.overallRating >= 4.0 -> CivicOrange
        else -> CivicRed
    }

    val formattedDate = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale("hi", "IN"))
        .format(Date(report.timestamp))

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Celebratory "Thank You" Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CivicGreen.copy(alpha = 0.1f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, CivicGreen.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CivicGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "धन्यवाद! आपकी राय दर्ज कर ली गई है।",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CivicGreen
                    )
                    Text(
                        text = "नीचे आपका आधिकारिक नागरिक रिपोर्ट कार्ड तैयार है:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // The Formal Report Card / Scorecard Document
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("official_report_card"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryNavy.copy(alpha = 0.2f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Official Card Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "सरपंच रिपोर्ट कार्ड",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryNavy
                        )
                        Text(
                            text = "5-वर्षीय कार्यकाल जन-मूल्यांकन पत्रक",
                            style = MaterialTheme.typography.labelSmall,
                            color = SecondarySaffron,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(PrimaryNavy.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = null,
                            tint = PrimaryNavy,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                Spacer(modifier = Modifier.height(14.dp))

                // Village, Panchayat, and Sarpanch Meta Info Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "ग्राम पंचायत: ",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = report.panchayatName,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "गांव: ",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = report.villageName,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "सरपंच: ",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = report.sarpanchName,
                                fontWeight = FontWeight.ExtraBold,
                                color = PrimaryNavy,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "मूल्यांकन दिनांक: $formattedDate",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Hero Overall Score Callout
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(scoreColor.copy(alpha = 0.15f), scoreColor.copy(alpha = 0.05f))
                            )
                        )
                        .border(1.5.dp, scoreColor.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "OVERALL SARPANCH RATING",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp,
                                color = scoreColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = AccentGold,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${report.overallRating} / 10",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = scoreColor
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = scoreColor
                        ) {
                            Text(
                                text = report.gradeTitle,
                                color = Color.White,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Category-by-Category Star Breakdown
                Text(
                    text = "विषय-वार रेटिंग विवरण (Category Breakdown)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DevelopmentCategory.entries.forEach { category ->
                        val rating = report.categoryRatings[category] ?: 0
                        val filledStars = "⭐".repeat(rating.coerceIn(0, 10))
                        val emptyStars = "☆".repeat((10 - rating).coerceIn(0, 10))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = category.hindiTitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "$rating/10",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = if (rating >= 7) CivicGreen else if (rating >= 5) PrimaryNavy else CivicRed
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                // Render the exact stars requested in the prompt: e.g. ⭐⭐⭐⭐⭐⭐⭐⭐☆☆ 8/10
                                Text(
                                    text = "$filledStars$emptyStars",
                                    fontSize = 14.sp,
                                    letterSpacing = 2.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Summary Card: “गांव की जनता की राय” (Required by user prompt)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFEF3C7)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.5f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = SecondarySaffron,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "गांव की जनता की राय",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF78350F)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val commentText = if (report.comment.isNotBlank()) {
                            report.comment
                        } else {
                            "इस मूल्यांकन में नागरिक द्वारा केवल स्टार रेटिंग दी गई है। समग्र कार्यकाल पर जनता का फैसला स्पष्ट है।"
                        }

                        Text(
                            text = "“$commentText”",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF451A03),
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Action Buttons: Share, View Dashboard, Rate Again
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Share on WhatsApp / Social Media
            Button(
                onClick = {
                    shareReportCard(context, report)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("share_report_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CivicGreen,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "व्हाट्सएप पर रिपोर्ट कार्ड शेयर करें 📲",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // View Public Results Dashboard
            Button(
                onClick = onViewDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("view_dashboard_from_report_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryNavy,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Insights,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "सभी गांवों के परिणाम व डैशबोर्ड देखें",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Rate Another Village / Sarpanch
            Button(
                onClick = onRateAnother,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("rate_another_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = SecondarySaffron
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, SecondarySaffron)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = SecondarySaffron,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "नया मूल्यांकन करें (Rate Again)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Builds formatted text summary and triggers native Android Share Intent.
 */
private fun shareReportCard(context: Context, report: SarpanchReport) {
    val builder = StringBuilder()
    builder.appendLine("📜 *सरपंच रिपोर्ट कार्ड — 5 साल का कार्यकाल*")
    builder.appendLine("━━━━━━━━━━━━━━━━━━━")
    builder.appendLine("🏛️ *ग्राम पंचायत:* ${report.panchayatName}")
    builder.appendLine("🏡 *गांव:* ${report.villageName}")
    builder.appendLine("👤 *सरपंच:* ${report.sarpanchName}")
    builder.appendLine("━━━━━━━━━━━━━━━━━━━")
    builder.appendLine("⭐ *OVERALL RATING: ${report.overallRating} / 10*")
    builder.appendLine("🏆 *परिणाम:* ${report.gradeTitle}")
    builder.appendLine("━━━━━━━━━━━━━━━━━━━")
    builder.appendLine("📊 *विषय-वार रिपोर्ट:*")

    DevelopmentCategory.entries.forEach { category ->
        val score = report.categoryRatings[category] ?: 0
        val filled = "⭐".repeat(score)
        val empty = "☆".repeat(10 - score)
        builder.appendLine("${category.hindiTitle}: $filled$empty $score/10")
    }

    if (report.comment.isNotBlank()) {
        builder.appendLine("━━━━━━━━━━━━━━━━━━━")
        builder.appendLine("💬 *जनता की राय:* “${report.comment}”")
    }

    builder.appendLine("━━━━━━━━━━━━━━━━━━━")
    builder.appendLine("🗳️ *सरपंच रिपोर्ट कार्ड ऐप* द्वारा जनहित में जारी।")

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, builder.toString())
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "सरपंच रिपोर्ट कार्ड शेयर करें")
    context.startActivity(shareIntent)
}
