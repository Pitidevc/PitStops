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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.pitstops.ui.theme.LuckiestGuy
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


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

        Text(
            text = "Resumen de\nPitStops",
            color = Color.White,
            fontFamily = LuckiestGuy,
            fontSize = 50.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp)
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
                GraficaBarras()
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

