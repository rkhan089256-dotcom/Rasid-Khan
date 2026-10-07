package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
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
import com.example.ui.viewmodel.DashboardStats
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    stats: DashboardStats,
    reports: List<SarpanchReport>,
    panchayats: List<String>,
    selectedFilter: String?,
    onSelectFilter: (String?) -> Unit,
    onReportClick: (SarpanchReport) -> Unit,
    onAddNewRating: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))

            // Header Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "परिणाम एवं जन-डैशबोर्ड",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                    Text(
                        text = "गांवों के सरपंचों का 5-वर्षीय समग्र रिपोर्ट कार्ड",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onAddNewRating,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondarySaffron,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.testTag("dashboard_add_rating_button")
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "रेटिंग दें", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Panchayat Filter Chips (Required: "गांव के अनुसार रिपोर्ट")
        item {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "पंचायत अनुसार फिल्टर करें:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // "All" chip
                    val isAllSelected = selectedFilter == null
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isAllSelected) PrimaryNavy else MaterialTheme.colorScheme.surfaceVariant,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isAllSelected) PrimaryNavy else Color(0xFFCBD5E1)
                        ),
                        modifier = Modifier.clickable { onSelectFilter(null) }
                    ) {
                        Text(
                            text = "सभी पंचायतें (${reports.size})",
                            fontSize = 13.sp,
                            fontWeight = if (isAllSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isAllSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }

                    // Available Panchayats
                    val distinctPanchayats = panchayats.ifEmpty {
                        listOf("ग्राम पंचायत रामपुर", "ग्राम पंचायत किशनपुर", "ग्राम पंचायत चंदनपुर", "ग्राम पंचायत हरिपुर")
                    }

                    distinctPanchayats.forEach { panchayat ->
                        val isSelected = selectedFilter == panchayat
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) PrimaryNavy else MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) PrimaryNavy else Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier.clickable { onSelectFilter(panchayat) }
                        ) {
                            Text(
                                text = panchayat,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                            )
                        }
                    }
                }
            }
        }

        // Top 4 KPI Metric Cards
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // KPI 1: Total Ratings Count
                    KpiCard(
                        title = "कुल नागरिक रेटिंग",
                        value = "${stats.totalRatingsCount}",
                        subtitle = "नागरिकों ने मूल्यांकन किया",
                        icon = Icons.Default.People,
                        color = PrimaryNavy,
                        modifier = Modifier.weight(1f)
                    )

                    // KPI 2: Overall Average Rating
                    KpiCard(
                        title = "औसत रेटिंग स्कोर",
                        value = "${stats.overallAverage} / 10",
                        subtitle = "10 में से औसत अंक",
                        icon = Icons.Default.Star,
                        color = AccentGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // KPI 3: Highest Rated Category
                    KpiCard(
                        title = "सर्वश्रेष्ठ कार्य",
                        value = stats.highestCategory?.first?.hindiTitle ?: "—",
                        subtitle = stats.highestCategory?.let { "${it.second} / 10 ⭐" } ?: "डेटा उपलब्ध नहीं",
                        icon = Icons.Default.TrendingUp,
                        color = CivicGreen,
                        modifier = Modifier.weight(1f)
                    )

                    // KPI 4: Lowest Rated Category
                    KpiCard(
                        title = "सर्वाधिक सुधार योग्य",
                        value = stats.lowestCategory?.first?.hindiTitle ?: "—",
                        subtitle = stats.lowestCategory?.let { "${it.second} / 10 ⭐" } ?: "डेटा उपलब्ध नहीं",
                        icon = Icons.Default.TrendingDown,
                        color = CivicRed,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Category-wise Average Rating Bars (Required: "हर कैटेगरी की Average Rating")
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("category_averages_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "हर विषय का औसत स्कोर (10 में से)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = null,
                            tint = PrimaryNavy,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    DevelopmentCategory.entries.forEach { category ->
                        val avg = stats.categoryAverages[category] ?: 0.0
                        val progress = (avg / 10.0).toFloat().coerceIn(0f, 1f)
                        val barColor = when {
                            avg >= 8.0 -> CivicGreen
                            avg >= 6.5 -> PrimaryNavy
                            avg >= 4.5 -> CivicOrange
                            else -> CivicRed
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = category.getIcon(),
                                        contentDescription = null,
                                        tint = barColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = category.hindiTitle,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Text(
                                    text = "$avg / 10",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = barColor
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = barColor,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Star Rating Distribution Section (Required: "स्टार रेटिंग का प्रतिशत/ग्राफ")
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "स्टार रेटिंग वितरण (Rating Distribution)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val total = if (stats.totalRatingsCount == 0) 1 else stats.totalRatingsCount

                    DistributionRow(
                        label = "9 - 10 ⭐ (शानदार कार्य)",
                        count = stats.distribution9To10,
                        total = total,
                        color = CivicGreen
                    )
                    DistributionRow(
                        label = "7 - 8 ⭐ (संतोषजनक)",
                        count = stats.distribution7To8,
                        total = total,
                        color = PrimaryNavy
                    )
                    DistributionRow(
                        label = "5 - 6 ⭐ (औसत प्रदर्शन)",
                        count = stats.distribution5To6,
                        total = total,
                        color = CivicOrange
                    )
                    DistributionRow(
                        label = "1 - 4 ⭐ (सुधार की जरूरत)",
                        count = stats.distribution1To4,
                        total = total,
                        color = CivicRed
                    )
                }
            }
        }

        // Recent Reviews Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "नागरिकों के हालिया रिपोर्ट कार्ड (${reports.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "टैप करके पूरा कार्ड देखें",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        // Reviews List
        items(reports, key = { it.id }) { report ->
            CitizenReviewCard(
                report = report,
                onClick = { onReportClick(report) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun KpiCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.08f)
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun DistributionRow(
    label: String,
    count: Int,
    total: Int,
    color: Color
) {
    val pct = (count.toFloat() / total.toFloat() * 100).toInt()
    val progress = (count.toFloat() / total.toFloat()).coerceIn(0f, 1f)

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$count ($pct%)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun CitizenReviewCard(
    report: SarpanchReport,
    onClick: () -> Unit
) {
    val scoreColor = when {
        report.overallRating >= 8.0 -> CivicGreen
        report.overallRating >= 6.0 -> PrimaryNavy
        report.overallRating >= 4.0 -> CivicOrange
        else -> CivicRed
    }

    val formattedDate = SimpleDateFormat("dd MMM yyyy", Locale("hi", "IN"))
        .format(Date(report.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("review_item_${report.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${report.panchayatName} (${report.villageName})",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "सरपंच: ${report.sarpanchName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = PrimaryNavy
                    )
                }

                // Overall Score pill
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = scoreColor.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, scoreColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${report.overallRating}/10",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = scoreColor
                        )
                    }
                }
            }

            if (report.comment.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "“${report.comment}”",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(8.dp),
                        maxLines = 2
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "मूल्यांकन: $formattedDate",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "रिपोर्ट कार्ड खोलें",
                        style = MaterialTheme.typography.labelSmall,
                        color = PrimaryNavy,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
