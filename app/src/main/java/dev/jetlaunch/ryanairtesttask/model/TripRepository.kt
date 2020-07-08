package dev.jetlaunch.ryanairtesttask.model

import dev.jetlaunch.ryanairtesttask.networking.RyanairApiService
import dev.jetlaunch.ryanairtesttask.networking.responsemodels.TripResponse
import dev.jetlaunch.ryanairtesttask.utils.toNetworkResponse
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class TripRepository(private val apiService: RyanairApiService) {

    suspend fun loadTrips(
        origin: String,
        destination: String,
        date: String,
        adults: Int,
        teens: Int,
        children: Int
    ): NetworkResponse<TripResponse> {
        return try {

            apiService.loadSearchResult(
                origin = origin,
                destination = destination,
                dateOut = date,
                adults = adults,
                teens = teens,
                children = children
            ).toNetworkResponse()

        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> NetworkResponse(errorMessage = "No internet connection")
                is HttpException -> {
                    if (e.code() == 404)
                        NetworkResponse(errorMessage = "Trip not found")
                    else
                        NetworkResponse(errorMessage = "Server error")
                }
                else -> NetworkResponse(errorMessage = "Server error")
            }
        }
    }

}