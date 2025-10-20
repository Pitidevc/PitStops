package com.example.pitstops.ui.addpitstop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarPitStopScreen() {
    // Variables del formulario
    var piloto by remember { mutableStateOf("") }
    var escuderia by remember { mutableStateOf("") }
    var tiempo by remember { mutableStateOf("") }
    var cambioNeumaticos by remember { mutableStateOf("") }
    var numeroNeumaticos by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var motivoFallo by remember { mutableStateOf("") }
    var mecanico by remember { mutableStateOf("") }
    var fechaHora by remember { mutableStateOf("") }

    // Opciones de los selects
    val pilotos = listOf("Lewis Hamilton", "Max Verstappen", "Fernando Alonso")
    val escuderias = listOf("Mercedes", "Red Bull", "Aston Martin")
    val cambios = listOf("Sí", "No")
    val estados = listOf("Completado", "Pendiente")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD32F2F)) // rojo base
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center) // ⬅️ Centra completamente el formulario
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registrar Pit Stop",
                fontSize = 22.sp,
                color = Color(0xFFD32F2F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Piloto ---
            var expandedPiloto by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedPiloto,
                onExpandedChange = { expandedPiloto = !expandedPiloto }
            ) {
                OutlinedTextField(
                    value = piloto,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Piloto") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPiloto) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedPiloto,
                    onDismissRequest = { expandedPiloto = false }
                ) {
                    pilotos.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                piloto = opcion
                                expandedPiloto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- Escudería ---
            var expandedEscuderia by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedEscuderia,
                onExpandedChange = { expandedEscuderia = !expandedEscuderia }
            ) {
                OutlinedTextField(
                    value = escuderia,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Escudería") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEscuderia) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedEscuderia,
                    onDismissRequest = { expandedEscuderia = false }
                ) {
                    escuderias.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                escuderia = opcion
                                expandedEscuderia = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- Tiempo total (s) ---
            OutlinedTextField(
                value = tiempo,
                onValueChange = { tiempo = it },
                label = { Text("Tiempo Total (s)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // --- Cambio de neumáticos ---
            var expandedCambio by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCambio,
                onExpandedChange = { expandedCambio = !expandedCambio }
            ) {
                OutlinedTextField(
                    value = cambioNeumaticos,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Cambio de Neumáticos") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCambio) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedCambio,
                    onDismissRequest = { expandedCambio = false }
                ) {
                    cambios.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                cambioNeumaticos = opcion
                                expandedCambio = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- Número de neumáticos ---
            OutlinedTextField(
                value = numeroNeumaticos,
                onValueChange = { numeroNeumaticos = it },
                label = { Text("Número de Neumáticos") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // --- Estado ---
            var expandedEstado by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedEstado,
                onExpandedChange = { expandedEstado = !expandedEstado }
            ) {
                OutlinedTextField(
                    value = estado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstado) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedEstado,
                    onDismissRequest = { expandedEstado = false }
                ) {
                    estados.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                estado = opcion
                                expandedEstado = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- Motivo del fallo ---
            OutlinedTextField(
                value = motivoFallo,
                onValueChange = { motivoFallo = it },
                label = { Text("Motivo del Fallo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // --- Mecánico principal ---
            OutlinedTextField(
                value = mecanico,
                onValueChange = { mecanico = it },
                label = { Text("Mecánico Principal") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // --- Fecha y hora ---
            OutlinedTextField(
                value = fechaHora,
                onValueChange = { fechaHora = it },
                label = { Text("Fecha y Hora del Pit Stop") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Botones ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Guardar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Guardar", color = Color.White)
                }
                Button(
                    onClick = { /* Cancelar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegistrarPitStopScreen() {
    RegistrarPitStopScreen()
}
