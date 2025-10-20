package com.example.pitstops.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.data.repository.PitStopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PitStopViewModel(
    private val repository: PitStopRepository = PitStopRepository()
) : ViewModel() {

    private val _pitStops = MutableStateFlow<List<PitStop>>(emptyList())
    val pitStops: StateFlow<List<PitStop>> = _pitStops

    fun cargarUltimosPitStops() {
        viewModelScope.launch {
            _pitStops.value = repository.obtenerUltimosPitStops()
        }
    }
}
