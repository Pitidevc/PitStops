package com.example.pitstops.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.data.repository.PitStopRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.Principal

class AddPitStopViewModel(
    private val repository: PitStopRepository = PitStopRepository()
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _pilotos = MutableStateFlow<List<String>>(emptyList())
    val pilotos: StateFlow<List<String>> = _pilotos

    private val _neumaticos = MutableStateFlow<List<String>>(emptyList())
    val neumaticos: StateFlow<List<String>> = _neumaticos

    private val _estados = MutableStateFlow<List<String>>(emptyList())
    val estados: StateFlow<List<String>> = _estados

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    // --- TAG para logs ---
    private val TAG = "AddPitStopViewModel"

    fun cargarDatosFormulario() {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d(TAG, "🔄 Iniciando carga de datos del formulario...")
            try {
                val listaPilotos = repository.obtenerPilotos()
                val listaNeumaticos = repository.obtenerNeumaticos()
                val listaEstados = repository.obtenerEstados()

                Log.d(TAG, "✅ Pilotos cargados: ${listaPilotos.size} -> $listaPilotos")
                Log.d(TAG, "✅ Neumáticos cargados: ${listaNeumaticos.size} -> $listaNeumaticos")
                Log.d(TAG, "✅ Estados cargados: ${listaEstados.size} -> $listaEstados")

                _pilotos.value = listaPilotos
                _neumaticos.value = listaNeumaticos
                _estados.value = listaEstados

                _mensaje.value = null
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al cargar datos: ${e.message}", e)
                _mensaje.value = "Error al cargar datos: ${e.message}"
            } finally {
                _isLoading.value = false
                Log.d(TAG, "✅ Carga de datos finalizada.")
            }
        }
    }

    fun obtenerEscuderiaPorPiloto(piloto: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            Log.d(TAG, "🔍 Buscando escudería para piloto: $piloto")
            try {
                val escuderia = repository.obtenerEscuderiaPorPiloto(piloto)
                if (escuderia != null) {
                    Log.d(TAG, "✅ Escudería encontrada: $escuderia")
                    onResult(escuderia)
                } else {
                    Log.w(TAG, "⚠️ No se encontró escudería para el piloto: $piloto")
                }
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al obtener escudería: ${e.message}", e)
                _mensaje.value = "Error al obtener escudería: ${e.message}"
            }
        }
    }

    fun registrarPitStop(
        piloto: String,
        escuderia: String,
        tiempo: Double,
        cambioNeumaticos: String,
        numNeumaticos: Int,
        estado: String,
        motivoFallo: String,
        mecanicoPrincipal: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            Log.d(TAG, "📝 Registrando Pit Stop...")
            Log.d(TAG, "Datos enviados -> Piloto: $piloto | Escudería: $escuderia | Tiempo: $tiempo | Cambio: $cambioNeumaticos | Neumáticos: $numNeumaticos | Estado: $estado | Motivo: $motivoFallo")

            try {
                val pitStop = PitStop(
                    piloto = piloto,
                    escuderia = escuderia,
                    tiempoTotal = tiempo,
                    cambioNeumaticos = cambioNeumaticos,
                    numNeumaticos = numNeumaticos,
                    estado = estado,
                    motivoFallo = motivoFallo,
                    mecanicoPrincipal = mecanicoPrincipal,
                    fechaHora = Timestamp.now()
                )
                repository.agregarPitStop(pitStop)
                _mensaje.value = "✅ Pit Stop registrado correctamente"
                Log.d(TAG, "✅ Pit Stop guardado exitosamente.")
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error al registrar Pit Stop: ${e.message}", e)
                _mensaje.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
