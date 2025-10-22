package com.example.pitstops.data.repository

import android.util.Log
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

        val nuevoDoc = pitStopsRef.document()
        val pitStopConId = pitStop.copy(id = nuevoDoc.id)

        nuevoDoc.set(pitStopConId).await()
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

    suspend fun obtenerEstados(): List<String> {
        val Snapshot = db.collection("estados")
            .document("tZAK8CG7dv4PYQALTfQ8")
            .get()
            .await()

        return Snapshot.get("estado") as? List<String> ?: emptyList()
    }

    suspend fun obtenerNeumaticos(): List<String> {
        val Snapshot = db.collection("neumaticos")
            .document("iGBWnOVhVkty24teI6o8")
            .get()
            .await()

        return Snapshot.get("tipo") as? List<String> ?: emptyList()
    }
    suspend fun obtenerUltimosPitStops(limit: Long = 7): List<PitStop> {
        return try {
            val snapshot = pitStopsRef
                .orderBy("fechaHora", Query.Direction.DESCENDING)
                .limit(limit)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(PitStop::class.java)
            }.reversed() // para mostrarlos de más antiguo a más reciente

        } catch (e: Exception) {
            emptyList()
        }
    }



}