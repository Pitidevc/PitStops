package com.example.pitstops

import com.example.pitstops.data.model.PitStop
import com.example.pitstops.data.repository.PitStopRepository
import com.example.pitstops.viewmodel.PitStopViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class PitStopViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private lateinit var viewModel: PitStopViewModel
    private lateinit var mockRepository: PitStopRepository

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockRepository = mock(PitStopRepository::class.java)
        viewModel = PitStopViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando se cargan los pitstops, el estado se actualiza correctamente`() = runTest {

        val fakeList = listOf(
            PitStop(id = "1", piloto = "Hamilton"),
            PitStop(id = "2", piloto = "Verstappen")
        )


        `when`(mockRepository.obtenerUltimosPitStops()).thenReturn(fakeList)


        viewModel.cargarUltimosPitStops()
        advanceUntilIdle()


        val result = viewModel.pitStops.first()
        assertEquals(2, result.size)
        assertEquals("Hamilton", result[0].piloto)
    }

    @Test
    fun `cuando no hay pitstops, los valores se reinician`() = runTest {
        `when`(mockRepository.obtenerUltimosPitStops()).thenReturn(emptyList())

        viewModel.cargarUltimosPitStops()

        val result = viewModel.pitStops.first()
        assertEquals(0, result.size)
        assertEquals(0.0, viewModel.mejorTiempo.first(), 0.001)
        assertEquals(0.0, viewModel.promedioTiempo.first(), 0.001)
        assertEquals(0, viewModel.totalParadas.first())
    }

    @Test
    fun `calcula correctamente el mejor tiempo`() = runTest {
        val fakeList = listOf(
            PitStop(tiempoTotal = 3.2),
            PitStop(tiempoTotal = 2.8),
            PitStop(tiempoTotal = 3.5)
        )
        `when`(mockRepository.obtenerUltimosPitStops()).thenReturn(fakeList)

        viewModel.cargarUltimosPitStops()
        advanceUntilIdle()
        assertEquals(2.8, viewModel.mejorTiempo.first(), 0.001)
    }

    @Test
    fun `calcula correctamente el promedio de tiempos`() = runTest {
        val fakeList = listOf(
            PitStop(tiempoTotal = 4.0),
            PitStop(tiempoTotal = 2.0)
        )
        `when`(mockRepository.obtenerUltimosPitStops()).thenReturn(fakeList)

        viewModel.cargarUltimosPitStops()
        advanceUntilIdle()
        assertEquals(3.0, viewModel.promedioTiempo.first(), 0.001)
    }

    @Test
    fun `actualiza correctamente el total de paradas`() = runTest {
        val fakeList = listOf(
            PitStop(id = "1"),
            PitStop(id = "2"),
            PitStop(id = "3")
        )
        `when`(mockRepository.obtenerUltimosPitStops()).thenReturn(fakeList)

        viewModel.cargarUltimosPitStops()
        advanceUntilIdle()
        assertEquals(3, viewModel.totalParadas.first())
    }

}
