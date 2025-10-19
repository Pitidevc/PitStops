package com.example.pitstops.data.repository

import com.example.pitstops.data.model.PitStop
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PitStopRepository {

    private val db = FirebaseFirestore.getInstance()
    private val pitStopsRef = db.collection("pitstops")
    private val pilotosRef = db.collection("pilotos")

    // 🟢 Agregar nuevo pit stop (con ID numérico o autogenerado)
    suspend fun agregarPitStop(pitStop: PitStop) {
        val snapshot = pitStopsRef.get().await()
        val nuevoId = (snapshot.size() + 1).toString()

        val pitStopConId = pitStop.copy(id = nuevoId)
        pitStopsRef.document(nuevoId).set(pitStopConId).await()
    }

    // 🟡 Obtener todos los pit stops
    suspend fun obtenerPitStops(): List<PitStop> {
        val result = pitStopsRef
            .orderBy("fechaHora", Query.Direction.DESCENDING)
            .get()
            .await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(PitStop::class.java)
        }
    }

    // 🔵 Eliminar un pit stop por su ID
    suspend fun eliminarPitStop(id: String) {
        pitStopsRef.document(id).delete().await()
    }

    // 🟣 Actualizar pit stop existente
    suspend fun actualizarPitStop(pitStop: PitStop) {
        pitStopsRef.document(pitStop.id).set(pitStop).await()
    }

    // 🔸 Obtener escudería según el piloto (para el formulario)
    suspend fun obtenerEscuderiaPorPiloto(nombrePiloto: String): String? {
        val result = pilotosRef.whereEqualTo("nombre", nombrePiloto).get().await()
        return result.documents.firstOrNull()?.getString("escuderia")
    }

    // 🔸 Obtener lista de pilotos (para dropdown)
    suspend fun obtenerPilotos(): List<String> {
        val result = pilotosRef.get().await()
        return result.documents.mapNotNull { it.getString("nombre") }
    }
}
