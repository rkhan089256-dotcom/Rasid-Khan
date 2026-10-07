package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.model.SarpanchReport
import com.example.data.repository.RatingRepository
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.RatingFormScreen
import com.example.ui.screens.ReportCardScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SecondarySaffron
import com.example.ui.viewmodel.SarpanchViewModel
import com.example.ui.viewmodel.SarpanchViewModelFactory
import kotlinx.coroutines.launch

enum class AppDestination(
    val hindiTitle: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val testTag: String
) {
    RATING_FORM("रेटिंग दें", Icons.Filled.Edit, Icons.Outlined.Edit, "nav_rating_form"),
    REPORT_CARD("रिपोर्ट कार्ड", Icons.Filled.Assessment, Icons.Outlined.Assessment, "nav_report_card"),
    DASHBOARD("डैशबोर्ड", Icons.Filled.Insights, Icons.Outlined.Insights, "nav_dashboard"),
    ABOUT("जानकारी", Icons.Filled.Info, Icons.Outlined.Info, "nav_about")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = RatingRepository(database.ratingDao())
        val viewModelFactory = SarpanchViewModelFactory(repository)

        setContent {
            MyApplicationTheme {
                val viewModel: SarpanchViewModel = viewModel(factory = viewModelFactory)
                SarpanchApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SarpanchApp(viewModel: SarpanchViewModel) {
    var currentDestination by remember { mutableStateOf(AppDestination.RATING_FORM) }
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val latestReport by viewModel.latestSubmittedReport.collectAsStateWithLifecycle()
    val allReports by viewModel.allRatings.collectAsStateWithLifecycle()
    val filteredReports by viewModel.filteredRatings.collectAsStateWithLifecycle()
    val panchayats by viewModel.panchayatList.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val dashboardStats by viewModel.dashboardStats.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Back handling: pop back to Home / Rating form if on another tab
    if (currentDestination != AppDestination.RATING_FORM) {
        BackHandler {
            currentDestination = AppDestination.RATING_FORM
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                AppDestination.entries.forEach { destination ->
                    val isSelected = currentDestination == destination

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (destination == AppDestination.REPORT_CARD && latestReport == null) {
                                // If user hasn't submitted yet, pick the latest community report to display
                                val fallbackReport = allReports.firstOrNull()
                                if (fallbackReport != null) {
                                    viewModel.setLatestReport(fallbackReport)
                                    currentDestination = AppDestination.REPORT_CARD
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("कृपया पहले किसी सरपंच की रेटिंग सबमिट करें।")
                                    }
                                }
                            } else {
                                currentDestination = destination
                            }
                        },
                        icon = {
                            if (destination == AppDestination.REPORT_CARD && latestReport != null) {
                                BadgedBox(badge = { Badge { Text("1") } }) {
                                    Icon(
                                        imageVector = if (isSelected) destination.filledIcon else destination.outlinedIcon,
                                        contentDescription = destination.hindiTitle
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) destination.filledIcon else destination.outlinedIcon,
                                    contentDescription = destination.hindiTitle
                                )
                            }
                        },
                        label = {
                            Text(
                                text = destination.hindiTitle,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryNavy,
                            selectedTextColor = PrimaryNavy,
                            indicatorColor = SecondarySaffron.copy(alpha = 0.2f),
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.testTag(destination.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    AppDestination.RATING_FORM -> {
                        RatingFormScreen(
                            formState = formState,
                            onVillageChange = viewModel::updateVillageName,
                            onPanchayatChange = viewModel::updatePanchayatName,
                            onSarpanchChange = viewModel::updateSarpanchName,
                            onQuickSelect = viewModel::setQuickSelection,
                            onCategoryRatingChange = viewModel::updateCategoryRating,
                            onCommentChange = viewModel::updateComment,
                            onSubmit = {
                                viewModel.submitRating { submittedReport ->
                                    currentDestination = AppDestination.REPORT_CARD
                                }
                            },
                            onViewDashboard = {
                                currentDestination = AppDestination.DASHBOARD
                            }
                        )
                    }

                    AppDestination.REPORT_CARD -> {
                        val reportToShow = latestReport ?: allReports.firstOrNull()
                        if (reportToShow != null) {
                            ReportCardScreen(
                                report = reportToShow,
                                onRateAnother = {
                                    viewModel.resetForm()
                                    currentDestination = AppDestination.RATING_FORM
                                },
                                onViewDashboard = {
                                    currentDestination = AppDestination.DASHBOARD
                                }
                            )
                        } else {
                            // Empty State
                            RatingFormScreen(
                                formState = formState,
                                onVillageChange = viewModel::updateVillageName,
                                onPanchayatChange = viewModel::updatePanchayatName,
                                onSarpanchChange = viewModel::updateSarpanchName,
                                onQuickSelect = viewModel::setQuickSelection,
                                onCategoryRatingChange = viewModel::updateCategoryRating,
                                onCommentChange = viewModel::updateComment,
                                onSubmit = {
                                    viewModel.submitRating {
                                        currentDestination = AppDestination.REPORT_CARD
                                    }
                                },
                                onViewDashboard = {
                                    currentDestination = AppDestination.DASHBOARD
                                }
                            )
                        }
                    }

                    AppDestination.DASHBOARD -> {
                        DashboardScreen(
                            stats = dashboardStats,
                            reports = filteredReports,
                            panchayats = panchayats,
                            selectedFilter = selectedFilter,
                            onSelectFilter = viewModel::setFilter,
                            onReportClick = { clickedReport ->
                                viewModel.setLatestReport(clickedReport)
                                currentDestination = AppDestination.REPORT_CARD
                            },
                            onAddNewRating = {
                                currentDestination = AppDestination.RATING_FORM
                            }
                        )
                    }

                    AppDestination.ABOUT -> {
                        AboutScreen(
                            onStartRating = {
                                currentDestination = AppDestination.RATING_FORM
                            }
                        )
                    }
                }
            }
        }
    }
}
