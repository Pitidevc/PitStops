package com.example.pitstops.ui.listpitstops

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.navigation.AppScreens
import com.example.pitstops.viewmodels.PitStopListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PitStopListScreen(
    navController: NavController,
    viewModel: PitStopListViewModel = viewModel()
) {
    val pitStops by viewModel.pitStops.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // 🔍 Filtrar por piloto
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

            // 🔙 Botón de retroceso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.navigate(AppScreens.FirstScreen.route) {
                        popUpTo(AppScreens.FirstScreen.route) { inclusive = true }
                    }
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                }
            }

            // 🏁 Título
            Text(
                text = "Listado de Pit Stops",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 🔍 Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                placeholder = { Text("Buscar por piloto...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // 🔄 Contenido
            if (loading) {
                CircularProgressIndicator()
            } else {
                if (filteredList.isEmpty()) {
                    Text("No se encontraron Pit Stops", color = Color.Gray)
                } else {
                    EncabezadoTabla()
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyColumn {
                        itemsIndexed(filteredList) { index, pitStop ->
                            PitStopRow(
                                index = index + 1,
                                pitStop = pitStop,
                                onEdit = {
                                    // ✅ Navegar a EditarPitStopScreen con el ID del pitstop
                                    navController.navigate("${AppScreens.EditPitStop.route}/${pitStop.id}")
                                },
                                onDelete = {
                                    coroutineScope.launch {
                                        viewModel.eliminarPitStop(pitStop.id)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EncabezadoTabla() {
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

        // 🎨 Estado con color
        val estadoColor = when (pitStop.estado.lowercase()) {
            "ok" -> Color(0xFF4CAF50)
            "retraso" -> Color(0xFFFFC107)
            "fallido" -> Color(0xFFD32F2F)
            else -> Color(0xFF9E9E9E)
        }

        Box(
            modifier = Modifier
                .background(estadoColor, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            Text(pitStop.estado, color = Color.White)
        }

        // 🛠️ Botones
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
