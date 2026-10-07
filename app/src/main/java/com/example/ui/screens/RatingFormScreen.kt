package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WaterDamage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DevelopmentCategory
import com.example.data.model.SarpanchReport
import com.example.ui.components.CategoryRatingCard
import com.example.ui.theme.AccentGold
import com.example.ui.theme.CivicGreen
import com.example.ui.theme.CivicOrange
import com.example.ui.theme.CivicRed
import com.example.ui.theme.CivicRedLight
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SecondarySaffron
import com.example.ui.viewmodel.FormUiState

data class QuickDemoVillage(
    val village: String,
    val panchayat: String,
    val sarpanch: String
)

val SampleVillages = listOf(
    QuickDemoVillage("रामपुर", "ग्राम पंचायत रामपुर", "श्री रामेश्वर यादव"),
    QuickDemoVillage("किशनपुर", "ग्राम पंचायत किशनपुर", "श्रीमती सुनीता देवी"),
    QuickDemoVillage("चंदनपुर", "ग्राम पंचायत चंदनपुर", "श्री मुकेश वर्मा"),
    QuickDemoVillage("हरिपुर", "ग्राम पंचायत हरिपुर", "श्री जगमोहन सिंह")
)

@Composable
fun RatingFormScreen(
    formState: FormUiState,
    onVillageChange: (String) -> Unit,
    onPanchayatChange: (String) -> Unit,
    onSarpanchChange: (String) -> Unit,
    onQuickSelect: (String, String, String) -> Unit,
    onCategoryRatingChange: (DevelopmentCategory, Int) -> Unit,
    onCommentChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onViewDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val scoreColor = when {
        formState.overallRating >= 8.0 -> CivicGreen
        formState.overallRating >= 6.0 -> PrimaryNavy
        formState.overallRating >= 4.0 -> CivicOrange
        else -> CivicRed
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp)
    ) {
        // Hero Header Section with Civic Trust Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(PrimaryNavy, Color(0xFF1E293B))
                    )
                )
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Civic Emblem / Badge
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(SecondarySaffron.copy(alpha = 0.25f))
                        .border(1.5.dp, SecondarySaffron, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = "Panchayat Emblem",
                        tint = AccentGold,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "सरपंच रिपोर्ट कार्ड",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "“आपकी राय — आपके गांव का रिपोर्ट कार्ड”",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFFFDE68A),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Civic badge pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x33FFFFFF)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = Color(0xFF86EFAC),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "5-वर्षीय कार्यकाल निष्पक्ष जन-मूल्यांकन",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Civic Survey Question Card (Required by user prompt)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFEF3C7)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B).copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SecondarySaffron),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HowToVote,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "मुख्य मूल्यांकन प्रश्न:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "“आपके गांव में 5 साल तक सरपंच के कार्यकाल के बाद आप सरपंच साहब को 10 में से कितने स्टार देना चाहेंगे?”",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF78350F),
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            // Village & Panchayat Details Card
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "गांव एवं पंचायत की जानकारी",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "अनिवार्य",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryNavy,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Quick Select Village Chips
                    Text(
                        text = "त्वरित चयन (Quick Select):",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SampleVillages.forEach { item ->
                            val isSelected = formState.villageName == item.village
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) PrimaryNavy else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.clickable {
                                    onQuickSelect(item.village, item.panchayat, item.sarpanch)
                                }
                            ) {
                                Text(
                                    text = item.village,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Village Name Input
                    OutlinedTextField(
                        value = formState.villageName,
                        onValueChange = onVillageChange,
                        label = { Text("गांव का नाम (Village Name)") },
                        placeholder = { Text("उदा. रामपुर, किशनपुर") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("village_input"),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.AccountBalance, contentDescription = null, tint = PrimaryNavy)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Gram Panchayat Name Input
                    OutlinedTextField(
                        value = formState.panchayatName,
                        onValueChange = onPanchayatChange,
                        label = { Text("ग्राम पंचायत का नाम (Gram Panchayat)") },
                        placeholder = { Text("उदा. ग्राम पंचायत रामपुर") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("panchayat_input"),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = PrimaryNavy)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sarpanch Name Input
                    OutlinedTextField(
                        value = formState.sarpanchName,
                        onValueChange = onSarpanchChange,
                        label = { Text("सरपंच का नाम (Sarpanch Name)") },
                        placeholder = { Text("उदा. श्री रामेश्वर यादव / श्रीमती सुनीता देवी") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("sarpanch_input"),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = PrimaryNavy)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryNavy
                        )
                    )
                }
            }

            // Real-Time Overall Score Indicator Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = scoreColor.copy(alpha = 0.08f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, scoreColor.copy(alpha = 0.4f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "लाइव औसत स्कोर (Live Score)",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${formState.overallRating} / 10",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = scoreColor
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = scoreColor,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = when {
                                formState.overallRating >= 8.5 -> "उत्कृष्ट"
                                formState.overallRating >= 7.0 -> "संतोषजनक"
                                formState.overallRating >= 5.0 -> "औसत"
                                formState.overallRating >= 3.0 -> "कमजोर"
                                else -> "खराब"
                            },
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            // Section Header: 10 Evaluation Categories
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "मूल्यांकन के 10 मुख्य विषय",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "1 से 10 स्टार चुनें",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            // The 10 Categories List
            DevelopmentCategory.entries.forEachIndexed { idx, category ->
                val rating = formState.ratings[category] ?: 7
                CategoryRatingCard(
                    category = category,
                    index = idx + 1,
                    currentRating = rating,
                    onRatingChanged = { newRating ->
                        onCategoryRatingChange(category, newRating)
                    }
                )
            }

            // "मेरी राय" - Comment Box
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Comment,
                            contentDescription = null,
                            tint = PrimaryNavy,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "“मेरी राय” (आपकी व्यक्तिगत टिप्पणी)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "गांव के विकास, किसी विशेष कार्य या समस्या के बारे में अपने विचार लिखें:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = formState.comment,
                        onValueChange = onCommentChange,
                        placeholder = {
                            Text("उदा. सड़क व पानी की व्यवस्था अच्छी रही, पर नालियों की सफाई नियमित होनी चाहिए...")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("comment_input"),
                        minLines = 3,
                        maxLines = 6,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryNavy
                        )
                    )
                }
            }

            // Neutral Disclaimer (Required by user prompt)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFEFF6FF)
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = PrimaryNavy,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "महत्वपूर्ण सूचना एवं डिस्क्लेमर:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "“यह रेटिंग आपके व्यक्तिगत अनुभव और राय पर आधारित होनी चाहिए। यह ऐप किसी सरपंच के पक्ष या विपक्ष में प्रचार नहीं करता है, यह केवल जनता की राय एकत्र करने वाला पब्लिक सर्वे है।”",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1E3A8A),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Error display if validation fails
            AnimatedVisibility(
                visible = formState.errorMessage != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = CivicRedLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CivicRed),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = CivicRed
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formState.errorMessage ?: "",
                            color = CivicRed,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Big "Submit Rating" Button
            Button(
                onClick = onSubmit,
                enabled = !formState.isSubmitting,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("submit_rating_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondarySaffron,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                if (formState.isSubmitting) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "सबमिट हो रहा है...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "रेटिंग सबमिट करें (Submit Rating) ⭐",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Secondary option: View public results dashboard
            Button(
                onClick = onViewDashboard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("view_dashboard_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryNavy
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryNavy)
            ) {
                Icon(
                    imageVector = Icons.Default.HowToVote,
                    contentDescription = null,
                    tint = PrimaryNavy,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "गांवों का रिजल्ट डैशबोर्ड देखें",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
