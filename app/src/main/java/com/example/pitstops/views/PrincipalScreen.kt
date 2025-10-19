package com.example.pitstops.views

import android.R.attr.data
import android.R.attr.description
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.example.pitstops.R
import com.example.pitstops.ui.theme.LuckiestGuy
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.example.pitstops.ui.theme.PitStopsTheme
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrincipalScreen()
        }

    }
}


@Composable
fun PrincipalScreen() {

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
                Text("🏁 Resumen Pits Stops",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(350.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
            ) {
                GraficaBarras()
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
fun GraficaBarras(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            val chart = BarChart(context)

            // Datos de ejemplo 📊
            val entries = listOf(
                BarEntry(1f, 2f),
                BarEntry(2f, 1.5f),
                BarEntry(3f, 3f),
                BarEntry(4f, 2.2f),
            )

            val dataSet = BarDataSet(entries, "Pit Stops").apply {
                color = android.graphics.Color.rgb(255, 193, 7) // amarillo
                valueTextColor = android.graphics.Color.BLACK
                valueTextSize = 12f
            }

            chart.data = BarData(dataSet)
            chart.description.text = "Rendimiento de Pit Stops"
            chart.setNoDataText("Aún no hay datos 📊")
            chart.setBackgroundColor(android.graphics.Color.WHITE)

            // Personalización opcional
            chart.axisLeft.textColor = android.graphics.Color.BLACK
            chart.axisRight.isEnabled = false
            chart.xAxis.textColor = android.graphics.Color.BLACK
            chart.legend.textColor = android.graphics.Color.BLACK

            chart.invalidate() // refresca el gráfico

            chart
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

