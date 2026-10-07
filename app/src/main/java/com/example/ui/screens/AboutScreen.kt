package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DevelopmentCategory
import com.example.ui.theme.AccentGold
import com.example.ui.theme.CivicGreen
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SecondarySaffron

@Composable
fun AboutScreen(
    onStartRating: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Mission Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(PrimaryNavy.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = null,
                            tint = PrimaryNavy,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "सरपंच रिपोर्ट कार्ड",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryNavy
                        )
                        Text(
                            text = "आपकी राय — आपके गांव का रिपोर्ट कार्ड",
                            style = MaterialTheme.typography.bodySmall,
                            color = SecondarySaffron,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "इस ऐप का मुख्य उद्देश्य ग्रामीण लोकतंत्र को सशक्त बनाना है, जिससे गांव के हर नागरिक को अपने पंचायत के 5-वर्षीय विकास कार्यों का निष्पक्ष मूल्यांकन करने का अधिकार मिले।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 22.sp
                )
            }
        }

        // Strict Neutrality & Public Survey Pledge
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEFF6FF)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Balance,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "निष्पक्षता एवं पारदर्शिता की नीति",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• यह ऐप किसी भी राजनीतिक दल या व्यक्ति के पक्ष या विपक्ष में नहीं है।\n• यह पूर्णतः स्वतंत्र जन-सर्वेक्षण (Public Feedback Survey) है।\n• यहां दी गई रेटिंग नागरिकों के जमीनी अनुभव पर आधारित होती है।\n• किसी भी झूठे प्रचार से दूर, पारदर्शी ग्रामीण विकास को बढ़ावा देना ही इसका उद्देश्य है।",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF1E3A8A),
                    lineHeight = 20.sp
                )
            }
        }

        // Star Rating Scale Explanation
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "10-स्टार रेटिंग का पैमाना",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                StarScaleItem("9 - 10 ⭐", "उत्कृष्ट कार्य", "कार्यकाल में सभी वादे पूरे हुए और गांव का सर्वांगीण विकास हुआ।", CivicGreen)
                StarScaleItem("7 - 8 ⭐", "संतोषजनक कार्य", "अधिकांश विकास कार्य हुए, कुछ मामूली सुधार अपेक्षित हैं।", PrimaryNavy)
                StarScaleItem("5 - 6 ⭐", "औसत प्रदर्शन", "काम हुए लेकिन गति धीमी रही और कई योजनाएं अधूरी रहीं।", SecondarySaffron)
                StarScaleItem("1 - 4 ⭐", "सुधार की सख्त जरूरत", "बुनियादी सुविधाएं (सड़क, नाली, पानी) नदारद या उपेक्षित रहीं।", Color(0xFFDC2626))
            }
        }

        // 10 Criteria Quick Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "मूल्यांकन के 10 विषय",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                DevelopmentCategory.entries.forEachIndexed { i, cat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${i + 1}. ${cat.hindiTitle}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.width(170.dp)
                        )
                        Text(
                            text = cat.englishTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun StarScaleItem(
    stars: String,
    title: String,
    desc: String,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.08f),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.2f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stars,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 13.sp,
                modifier = Modifier.width(75.dp)
            )
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = color,
                    fontSize = 13.sp,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
