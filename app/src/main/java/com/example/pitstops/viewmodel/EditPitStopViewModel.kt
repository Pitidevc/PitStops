package com.example.pitstops.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.data.repository.PitStopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditPitStopViewModel(
    private val repository: PitStopRepository = PitStopRepository()
) : ViewModel() {

    private val _pitStop = MutableStateFlow<PitStop?>(null)
    val pitStop: StateFlow<PitStop?> = _pitStop

    private val _pilotos = MutableStateFlow<List<String>>(emptyList())
    val pilotos: StateFlow<List<String>> = _pilotos

    private val _neumaticos = MutableStateFlow<List<String>>(emptyList())
    val neumaticos: StateFlow<List<String>> = _neumaticos

    private val _estados = MutableStateFlow<List<String>>(emptyList())
    val estados: StateFlow<List<String>> = _estados

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun cargarDatosFormulario() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _pilotos.value = repository.obtenerPilotos()
                _neumaticos.value = repository.obtenerNeumaticos()
                _estados.value = repository.obtenerEstados()
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar datos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarPitStopPorId(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val lista = repository.obtenerPitStops()
                _pitStop.value = lista.find { it.id == id }
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar el Pit Stop"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun actualizarPitStop(pitStop: PitStop) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.actualizarPitStop(pitStop)
                _mensaje.value = "✅ Pit Stop actualizado correctamente"
            } catch (e: Exception) {
                _mensaje.value = "❌ Error al actualizar"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun obtenerEscuderiaPorPiloto(nombrePiloto: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            val escuderia = repository.obtenerEscuderiaPorPiloto(nombrePiloto)
            escuderia?.let { onResult(it) }
        }
    }
}
