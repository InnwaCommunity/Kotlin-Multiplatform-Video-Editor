package com.manage.videoeditor.ui.screens.main

import com.manage.videoeditor.domain.repositories.SettingsRepository
import com.manage.videoeditor.utils.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(private val settingsRepository: SettingsRepository) {
    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

}