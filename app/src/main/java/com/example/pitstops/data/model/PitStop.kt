package com.example.pitstops.data.model

import java.sql.Timestamp

data class PitStop(
    val id : String = "",
    val  piloto : String  = "",
    val escuderia : String = "",
    val tiempoTotal : Double = 0.0,
    val cambioNumaticos : String = "",
    val numNeumaticos : Int = 0,
    val estado: String = "",
    val motivoFallo : String = "",
    val fechaHora : Timestamp?=null
)
