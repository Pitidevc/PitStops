package com.example.pitstops.ui.principalView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.pitstops.R
import com.example.pitstops.data.model.PitStop
import com.example.pitstops.ui.theme.LuckiestGuy
import com.example.pitstops.ui.viewmodel.PitStopViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.Timestamp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrincipalScreen()
        }

    }
}


@Composable
fun PrincipalScreen(viewModel: PitStopViewModel = PitStopViewModel()) {
    // This line was causing the error. With the import, it will now compile.
    val pitStops by viewModel.pitStops.collectAsState()
    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.cargarUltimosPitStops()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF850900)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )



        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 100.dp)
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Resumen de\nPitStops",
                color = Color.White,
                fontFamily = LuckiestGuy,
                fontSize = 50.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 70.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.guidocars),
                contentDescription = null,
                modifier = Modifier
                    .width(300.dp)
                    .height(220.dp)
                    .offset(y = (40).dp)
                    .zIndex(1f)
            )

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(150.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("🏁 Mejor Pit Stop",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(350.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                GraficaPitStops(pitStops)
            }

            Button(
                onClick = { /* Aquí va lo que hace el botón */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(70.dp)
                    .padding(top = 15.dp)
            ) {
                Text("Registrar PitStop")
            }

            Button(
                onClick = { /* Aquí va lo que hace el botón */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(250.dp)
                    .height(70.dp)
                    .padding(top = 15.dp)
            ) {
                Text("Ver Listado")
            }

        }
    }
}

@Composable
fun GraficaPitStops(pitStops: List<PitStop>, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                setBackgroundColor(android.graphics.Color.WHITE)
                setNoDataText("Cargando datos...")
            }
        },
        update = { chart ->
            if (pitStops.isNotEmpty()) {
                val entries = pitStops.mapIndexed { index, pit ->
                    BarEntry(index.toFloat(), pit.tiempoTotal.toFloat())
                }

                val labels = pitStops.map { pit ->
                    (pit.fechaHora as? Timestamp)?.toDate()?.toString()?.takeLast(8) ?: "?"                }

                val dataSet = BarDataSet(entries, "Tiempo de Pit Stops (s)").apply {
                    color = android.graphics.Color.rgb(255, 193, 7)
                    valueTextColor = android.graphics.Color.BLACK
                    valueTextSize = 12f
                }

                val data = BarData(dataSet)
                chart.data = data

                val xAxis = chart.xAxis
                xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.textColor = android.graphics.Color.BLACK
                chart.axisLeft.textColor = android.graphics.Color.BLACK
                chart.axisRight.isEnabled = false
                chart.legend.textColor = android.graphics.Color.BLACK
                chart.description.text = "Últimos PitStops"
                chart.invalidate()
            }
        },
        modifier = modifier
            .width(350.dp)
            .height(350.dp)
            .padding(16.dp)
    )
}





@Preview
@Composable
fun Previewco(){
    PrincipalScreen()
}