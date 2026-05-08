package com.waarstreamt.het.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waarstreamt.het.data.model.ContentDetail
import com.waarstreamt.het.data.model.SearchResult
import com.waarstreamt.het.data.repository.ContentRepository
import com.waarstreamt.het.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val suggestions: List<SearchResult> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
)

data class DetailUiState(
    val detail: ContentDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ContentRepository,
) : ViewModel() {

    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    private val _queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _queryFlow
                .debounce(300)
                .distinctUntilChanged()
                .filter { it.length >= 2 }
                .collect { query -> performSearch(query) }
        }
    }

    fun onQueryChange(query: String) {
        _queryFlow.value = query
        _searchUiState.value = _searchUiState.value.copy(
            query = query,
            suggestions = if (query.length < 2) emptyList() else _searchUiState.value.suggestions,
        )
        if (query.length < 2) {
            _searchUiState.value = _searchUiState.value.copy(suggestions = emptyList())
        }
    }

    private suspend fun performSearch(query: String) {
        _searchUiState.value = _searchUiState.value.copy(isSearching = true, error = null)
        when (val result = repository.search(query)) {
            is Result.Success -> _searchUiState.value = _searchUiState.value.copy(
                suggestions = result.data,
                isSearching = false,
            )
            is Result.Error -> _searchUiState.value = _searchUiState.value.copy(
                error = result.message,
                isSearching = false,
            )
            is Result.Loading -> Unit
        }
    }

    fun loadDetail(id: String) {
        _detailUiState.value = DetailUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = repository.getDetail(id)) {
                is Result.Success -> _detailUiState.value = DetailUiState(detail = result.data)
                is Result.Error -> _detailUiState.value = DetailUiState(error = result.message)
                is Result.Loading -> Unit
            }
        }
    }

    fun clearDetail() {
        _detailUiState.value = DetailUiState()
    }
}
