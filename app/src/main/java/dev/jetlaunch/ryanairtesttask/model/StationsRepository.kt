package dev.jetlaunch.ryanairtesttask.model

import dev.jetlaunch.ryanairtesttask.entity.EntityStation
import dev.jetlaunch.ryanairtesttask.networking.RyanairApiService
import dev.jetlaunch.ryanairtesttask.utils.toNetworkResponse
import java.lang.Exception
import java.net.UnknownHostException

class StationsRepository(private val apiService: RyanairApiService) {

    suspend fun loadStations(): NetworkResponse<List<EntityStation>> {
        return try {
            apiService.loadStations().stationsList.toNetworkResponse()

        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> NetworkResponse(errorMessage = "No internet connection")
                else -> NetworkResponse(errorMessage = "Server error")
            }
        }
    }

}