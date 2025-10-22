package com.example.pitstops.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.data.repository.PitStopRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PitStopListViewModel(
    private val repository: PitStopRepository = PitStopRepository()
) : ViewModel() {

    private val _pitStops = MutableStateFlow<List<PitStop>>(emptyList())
    val pitStops: StateFlow<List<PitStop>> = _pitStops

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje
    init {
        cargarPitStops()
    }

    fun cargarPitStops() {
        viewModelScope.launch {
            _loading.value = true
            _pitStops.value = repository.obtenerPitStops()
            _loading.value = false
        }
    }

    fun eliminarPitStop(id: String) {
        viewModelScope.launch {
            try {
                repository.eliminarPitStop(id)
                _mensaje.value = "✅ Eliminado correctamente"
                cargarPitStops()
            } catch (e: Exception) {
                _mensaje.value = "❌ Error al eliminar"
            }
        }
    }


}
