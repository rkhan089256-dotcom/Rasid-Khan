package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DevelopmentCategory
import com.example.ui.theme.AccentGold
import com.example.ui.theme.CivicGreen
import com.example.ui.theme.CivicOrange
import com.example.ui.theme.CivicRed
import com.example.ui.theme.PrimaryNavy

@Composable
fun CategoryRatingCard(
    category: DevelopmentCategory,
    index: Int,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val scoreColor = when {
        currentRating >= 8 -> CivicGreen
        currentRating >= 6 -> PrimaryNavy
        currentRating >= 4 -> CivicOrange
        else -> CivicRed
    }

    val scoreLabel = when (currentRating) {
        10 -> "सर्वश्रेष्ठ (10/10)"
        9 -> "शानदार कार्य (9/10)"
        8 -> "बहुत अच्छा (8/10)"
        7 -> "अच्छा (7/10)"
        6 -> "संतोषजनक (6/10)"
        5 -> "औसत (5/10)"
        4 -> "सुधार योग्य (4/10)"
        3 -> "कमजोर (3/10)"
        2 -> "खराब (2/10)"
        else -> "अति दयनीय (1/10)"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("category_card_${category.id}"),
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
            // Header: Category Icon, Title, Index, and Current Score Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(scoreColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = category.getIcon(),
                            contentDescription = category.hindiTitle,
                            tint = scoreColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$index. ${category.hindiTitle}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = category.englishTitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                // Score Badge Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = scoreColor.copy(alpha = 0.12f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, scoreColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$currentRating/10",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = scoreColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = category.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Interactive 10 Stars Row (Responsive, tap-friendly)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (star in 1..10) {
                    val isFilled = star <= currentRating
                    val scale by animateFloatAsState(
                        targetValue = if (star == currentRating) 1.22f else 1.0f,
                        label = "star_scale"
                    )

                    Box(
                        modifier = Modifier
                            .scale(scale)
                            .minimumInteractiveComponentSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onRatingChanged(star)
                            }
                            .testTag("star_${category.id}_$star"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isFilled) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Rating $star of 10",
                            tint = if (isFilled) AccentGold else Color(0xFFCBD5E1),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Quick Tap Number Selector Pills (1 to 10)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                for (num in 1..10) {
                    val isSelected = num == currentRating
                    val bg by animateColorAsState(
                        targetValue = if (isSelected) scoreColor else MaterialTheme.colorScheme.surfaceVariant,
                        label = "pill_bg"
                    )
                    val textCol by animateColorAsState(
                        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        label = "pill_text"
                    )

                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(bg)
                            .border(
                                width = if (isSelected) 1.5.dp else 0.5.dp,
                                color = if (isSelected) scoreColor else Color(0xFFCBD5E1),
                                shape = CircleShape
                            )
                            .clickable { onRatingChanged(num) }
                            .testTag("pill_${category.id}_$num"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = num.toString(),
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                            color = textCol
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rating Label Text
            Text(
                text = "रेटिंग: $scoreLabel",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = scoreColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
