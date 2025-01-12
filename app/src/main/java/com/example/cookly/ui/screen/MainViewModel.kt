package com.example.cookly.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookly.database.FoodDao
import com.example.cookly.model.Food
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(dao: FoodDao): ViewModel() {

    val data: StateFlow<List<Food>> = dao.getFood().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0L),
        initialValue = emptyList()
    )

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    val filteredData: StateFlow<List<Food>> = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(data) { text, allData ->
            if (text.isBlank()) {
                allData
            } else {
                allData.filter {
                    it.doesMacthSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(0),
            emptyList()
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}