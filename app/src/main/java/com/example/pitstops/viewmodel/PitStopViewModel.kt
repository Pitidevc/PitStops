package com.example.pitstops.viewmodel

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
    private val _mejorTiempo = MutableStateFlow(0.0)
    val mejorTiempo: StateFlow<Double> = _mejorTiempo

    private val _promedioTiempo = MutableStateFlow(0.0)
    val promedioTiempo: StateFlow<Double> = _promedioTiempo

    private val _totalParadas = MutableStateFlow(0)
    val totalParadas: StateFlow<Int> = _totalParadas

    fun cargarUltimosPitStops() {
        viewModelScope.launch {
            val lista = repository.obtenerUltimosPitStops()
            _pitStops.value = lista

            if (lista.isNotEmpty()) {
                _mejorTiempo.value = lista.minOf { it.tiempoTotal }
                _promedioTiempo.value = lista.map { it.tiempoTotal }.average()
                _totalParadas.value = lista.size
            } else {
                _mejorTiempo.value = 0.0
                _promedioTiempo.value = 0.0
                _totalParadas.value = 0
            }
        }
    }
}
