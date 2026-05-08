package com.waarstreamt.het.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waarstreamt.het.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository,
) : ViewModel() {

    val language: StateFlow<String> = repo.language.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), "Nederlands",
    )

    val country: StateFlow<String> = repo.country.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), "Nederland",
    )

    val ownedServices: StateFlow<Set<String>> = repo.ownedServices.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet(),
    )

    fun setLanguage(value: String) {
        viewModelScope.launch { repo.setLanguage(value) }
    }

    fun setCountry(value: String) {
        viewModelScope.launch { repo.setCountry(value) }
    }

    fun toggleService(serviceId: String) {
        viewModelScope.launch {
            val current = ownedServices.value
            repo.setOwnedServices(
                if (serviceId in current) current - serviceId else current + serviceId,
            )
        }
    }
}
