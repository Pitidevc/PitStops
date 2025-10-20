package com.example.pitstops.data.model

import com.google.firebase.Timestamp


data class PitStop(
    val id : String = "",
    val  piloto : String  = "",
    val escuderia : String = "",
    val tiempoTotal : Double = 0.0,
    val cambioNeumaticos : String = "",
    val numNeumaticos : Int = 0,
    val estado: String = "",
    val motivoFallo : String = "",
    val fechaHora : Timestamp?=null
)
