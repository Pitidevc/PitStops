package com.example.pitstops.ui.editpitstop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.navigation.AppScreens
import com.example.pitstops.ui.addpitstop.DropdownCampo
import com.example.pitstops.viewmodel.EditPitStopViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPitStopScreen(
    navController: NavController,
    pitStopId: String, // 👈 se recibe el ID del pitstop a editar
    viewModel: EditPitStopViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val pilotos by viewModel.pilotos.collectAsStateWithLifecycle()
    val neumaticos by viewModel.neumaticos.collectAsStateWithLifecycle()
    val estados by viewModel.estados.collectAsStateWithLifecycle()
    val mensaje by viewModel.mensaje.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val pitStop by viewModel.pitStop.collectAsStateWithLifecycle()

    // Cargar datos al abrir pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarDatosFormulario()
        viewModel.cargarPitStopPorId(pitStopId)
    }

    // Variables del formulario
    var piloto by remember { mutableStateOf("") }
    var escuderia by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var cambioNeumaticos by remember { mutableStateOf("") }
    var numeroNeumaticos by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var motivoFallo by remember { mutableStateOf("") }
    var mecanicoPrincipal by remember { mutableStateOf("") }

    // Actualizar campos cuando se cargue el pitstop
    LaunchedEffect(pitStop) {
        pitStop?.let {
            piloto = it.piloto
            escuderia = it.escuderia
            tiempo = it.tiempoTotal.toString()
            cambioNeumaticos = it.cambioNeumaticos
            numeroNeumaticos = it.numNeumaticos.toString()
            estado = it.estado
            motivoFallo = it.motivoFallo
            mecanicoPrincipal = it.mecanicoPrincipal
        }
    }

    // Actualizar escudería automáticamente
    LaunchedEffect(piloto) {
        if (piloto.isNotBlank()) {
            viewModel.obtenerEscuderiaPorPiloto(piloto) {
                escuderia = it
            }
        }
    }

    // Volver al inicio si se guarda correctamente
    LaunchedEffect(mensaje) {
        if (mensaje?.contains("✅") == true) {
            delay(1500)
            navController.navigate(AppScreens.FirstScreen.route) {
                popUpTo(AppScreens.FirstScreen.route) { inclusive = true }
            }
        }
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD32F2F))
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.9f)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Editar Pit Stop",
                    fontSize = 22.sp,
                    color = Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DropdownCampo("Piloto", piloto, pilotos) { piloto = it }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = escuderia,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Escudería (auto)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = tiempo,
                    onValueChange = { tiempo = it },
                    label = { Text("Tiempo Total (s)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                DropdownCampo("Cambio de Neumáticos", cambioNeumaticos, neumaticos) {
                    cambioNeumaticos = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = numeroNeumaticos,
                    onValueChange = { numeroNeumaticos = it },
                    label = { Text("Número de Neumáticos") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                DropdownCampo("Estado", estado, estados) { estado = it }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = mecanicoPrincipal,
                    onValueChange = { mecanicoPrincipal = it },
                    label = { Text("Mecánico Principal") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = motivoFallo,
                    onValueChange = { motivoFallo = it },
                    label = { Text("Motivo del Fallo") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            val pitStopEditado = PitStop(
                                id = pitStopId,
                                piloto = piloto,
                                escuderia = escuderia,
                                tiempoTotal = tiempo.toDoubleOrNull() ?: 0.0,
                                cambioNeumaticos = cambioNeumaticos,
                                numNeumaticos = numeroNeumaticos.toIntOrNull() ?: 0,
                                estado = estado,
                                motivoFallo = motivoFallo,
                                mecanicoPrincipal = mecanicoPrincipal,
                                fechaHora = pitStop?.fechaHora // mantener la original
                            )

                            viewModel.actualizarPitStop(pitStopEditado)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text("Guardar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancelar", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                mensaje?.let {
                    Text(
                        text = it,
                        color = if (it.contains("✅")) Color(0xFF388E3C) else Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}
