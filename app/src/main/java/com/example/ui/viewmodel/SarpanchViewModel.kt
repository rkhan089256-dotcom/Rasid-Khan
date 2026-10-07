package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.DevelopmentCategory
import com.example.data.model.SarpanchReport
import com.example.data.repository.RatingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class FormUiState(
    val villageName: String = "",
    val panchayatName: String = "",
    val sarpanchName: String = "",
    val ratings: Map<DevelopmentCategory, Int> = DevelopmentCategory.entries.associateWith { 7 },
    val comment: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null
) {
    val overallRating: Double
        get() {
            if (ratings.isEmpty()) return 0.0
            val sum = ratings.values.sum()
            return Math.round((sum.toDouble() / ratings.size.toDouble()) * 10.0) / 10.0
        }
}

data class DashboardStats(
    val totalRatingsCount: Int = 0,
    val overallAverage: Double = 0.0,
    val categoryAverages: Map<DevelopmentCategory, Double> = emptyMap(),
    val highestCategory: Pair<DevelopmentCategory, Double>? = null,
    val lowestCategory: Pair<DevelopmentCategory, Double>? = null,
    val distribution9To10: Int = 0,
    val distribution7To8: Int = 0,
    val distribution5To6: Int = 0,
    val distribution1To4: Int = 0
)

class SarpanchViewModel(private val repository: RatingRepository) : ViewModel() {

    private val _formState = MutableStateFlow(FormUiState())
    val formState: StateFlow<FormUiState> = _formState.asStateFlow()

    private val _selectedFilter = MutableStateFlow<String?>(null)
    val selectedFilter: StateFlow<String?> = _selectedFilter.asStateFlow()

    val allRatings: StateFlow<List<SarpanchReport>> = repository.allRatings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val panchayatList: StateFlow<List<String>> = repository.allPanchayats
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredRatings: StateFlow<List<SarpanchReport>> = combine(allRatings, _selectedFilter) { list, filter ->
        if (filter.isNullOrBlank()) list else list.filter { it.panchayatName.equals(filter, ignoreCase = true) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val dashboardStats: StateFlow<DashboardStats> = filteredRatings.combine(_selectedFilter) { reports, _ ->
        computeStats(reports)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DashboardStats()
    )

    private val _latestSubmittedReport = MutableStateFlow<SarpanchReport?>(null)
    val latestSubmittedReport: StateFlow<SarpanchReport?> = _latestSubmittedReport.asStateFlow()

    fun updateVillageName(name: String) {
        _formState.value = _formState.value.copy(
            villageName = name,
            errorMessage = null
        )
    }

    fun updatePanchayatName(name: String) {
        _formState.value = _formState.value.copy(
            panchayatName = name,
            errorMessage = null
        )
    }

    fun updateSarpanchName(name: String) {
        _formState.value = _formState.value.copy(
            sarpanchName = name,
            errorMessage = null
        )
    }

    fun setQuickSelection(village: String, panchayat: String, sarpanch: String) {
        _formState.value = _formState.value.copy(
            villageName = village,
            panchayatName = panchayat,
            sarpanchName = sarpanch,
            errorMessage = null
        )
    }

    fun updateCategoryRating(category: DevelopmentCategory, rating: Int) {
        val safeRating = rating.coerceIn(1, 10)
        val updatedMap = _formState.value.ratings.toMutableMap()
        updatedMap[category] = safeRating
        _formState.value = _formState.value.copy(ratings = updatedMap)
    }

    fun updateComment(text: String) {
        _formState.value = _formState.value.copy(comment = text)
    }

    fun setFilter(panchayat: String?) {
        _selectedFilter.value = panchayat
    }

    fun setLatestReport(report: SarpanchReport) {
        _latestSubmittedReport.value = report
    }

    fun resetForm() {
        _formState.value = FormUiState()
    }

    fun submitRating(onSuccess: (SarpanchReport) -> Unit) {
        val state = _formState.value
        val village = state.villageName.trim()
        val panchayat = state.panchayatName.trim()
        val sarpanch = state.sarpanchName.trim()

        if (village.isBlank()) {
            _formState.value = state.copy(errorMessage = "कृपया अपने गांव का नाम दर्ज करें।")
            return
        }
        if (panchayat.isBlank()) {
            _formState.value = state.copy(errorMessage = "कृपया ग्राम पंचायत का नाम दर्ज करें।")
            return
        }
        if (sarpanch.isBlank()) {
            _formState.value = state.copy(errorMessage = "कृपया सरपंच साहब का नाम दर्ज करें।")
            return
        }

        _formState.value = state.copy(isSubmitting = true, errorMessage = null)

        val newReport = SarpanchReport(
            villageName = village,
            panchayatName = panchayat,
            sarpanchName = sarpanch,
            categoryRatings = state.ratings,
            comment = state.comment.trim(),
            timestamp = System.currentTimeMillis()
        )

        viewModelScope.launch {
            val insertedId = repository.insertRating(newReport)
            val finalReport = newReport.copy(id = insertedId)
            _latestSubmittedReport.value = finalReport
            _formState.value = FormUiState(
                villageName = village,
                panchayatName = panchayat,
                sarpanchName = sarpanch
            ) // Retain village/panchayat context
            onSuccess(finalReport)
        }
    }

    private fun computeStats(reports: List<SarpanchReport>): DashboardStats {
        if (reports.isEmpty()) return DashboardStats()

        val total = reports.size
        val overallAvg = Math.round((reports.map { it.overallRating }.average()) * 10.0) / 10.0

        val categoryAvgMap = mutableMapOf<DevelopmentCategory, Double>()
        DevelopmentCategory.entries.forEach { category ->
            val scores = reports.mapNotNull { it.categoryRatings[category] }
            if (scores.isNotEmpty()) {
                val avg = Math.round((scores.average()) * 10.0) / 10.0
                categoryAvgMap[category] = avg
            } else {
                categoryAvgMap[category] = 0.0
            }
        }

        val sorted = categoryAvgMap.entries.sortedByDescending { it.value }
        val highest = sorted.firstOrNull()?.let { it.key to it.value }
        val lowest = sorted.lastOrNull()?.let { it.key to it.value }

        var d9to10 = 0
        var d7to8 = 0
        var d5to6 = 0
        var d1to4 = 0

        reports.forEach { report ->
            when {
                report.overallRating >= 8.5 -> d9to10++
                report.overallRating >= 6.5 -> d7to8++
                report.overallRating >= 4.5 -> d5to6++
                else -> d1to4++
            }
        }

        return DashboardStats(
            totalRatingsCount = total,
            overallAverage = overallAvg,
            categoryAverages = categoryAvgMap,
            highestCategory = highest,
            lowestCategory = lowest,
            distribution9To10 = d9to10,
            distribution7To8 = d7to8,
            distribution5To6 = d5to6,
            distribution1To4 = d1to4
        )
    }
}

class SarpanchViewModelFactory(private val repository: RatingRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SarpanchViewModel::class.java)) {
            return SarpanchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
