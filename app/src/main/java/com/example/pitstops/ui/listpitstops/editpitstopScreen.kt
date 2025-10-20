package com.example.pitstops.ui.listpitstops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Timestamp

// ✅ Modelo real (igual al tuyo)
data class PitStop(
    val id: String = "",
    val piloto: String = "",
    val escuderia: String = "",
    val tiempoTotal: Double = 0.0,
    val cambioNumaticos: String = "",
    val numNeumaticos: Int = 0,
    val estado: String = "",
    val motivoFallo: String = "",
    val fechaHora: Timestamp? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PitStopListScreen( navController: NavController) {
    // 🔸 Datos de ejemplo (luego reemplazables por los de Firebase)
    var pitStops by remember {
        mutableStateOf(
            listOf(
                PitStop("1", "Oliveiro", "Mercedes", 2.4, "Sí", 4, "OK", "", null),
                PitStop("2", "James", "Red Bull", 2.8, "Sí", 4, "Fallido", "Problema en tuerca", null),
                PitStop("3", "Mark", "Aston Martin", 2.3, "No", 0, "OK", "", null),
                PitStop("4", "Sebastian", "Red Bull", 3.1, "Sí", 4, "Fallido", "Mala alineación", null),
                PitStop("5", "Lucas", "Mercedes", 3.0, "Sí", 4, "Fallido", "Pérdida de tiempo", null)
            )
        )
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 🔍 Filtrar los resultados según el texto ingresado
    val filteredList = pitStops.filter {
        it.piloto.contains(searchQuery.text, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Listado de Pit Stops",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 🔹 Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("Buscar por piloto...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 🔹 Encabezado de la tabla
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Núm.", fontWeight = FontWeight.Bold)
                Text("Piloto", fontWeight = FontWeight.Bold)
                Text("Tiempo (s)", fontWeight = FontWeight.Bold)
                Text("Estado", fontWeight = FontWeight.Bold)
                Text("Acciones", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                itemsIndexed(filteredList) { index, pitStop ->
                    PitStopRow(
                        index = index + 1,
                        pitStop = pitStop,
                        onEdit = {
                            // Aquí iría la navegación o edición
                        },
                        onDelete = {
                            // Eliminar (en Firebase luego)
                            pitStops = pitStops.toMutableList().apply { remove(pitStop) }
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun PitStopRow(
    index: Int,
    pitStop: PitStop,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(index.toString())
        Text(pitStop.piloto)
        Text(pitStop.tiempoTotal.toString())

        val estadoColor = if (pitStop.estado == "OK") Color(0xFF4CAF50) else Color(0xFFD32F2F)
        Box(
            modifier = Modifier
                .background(estadoColor, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(pitStop.estado, color = Color.White)
        }

        Row {
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF1976D2))
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFD32F2F))
            }
        }
    }
}


