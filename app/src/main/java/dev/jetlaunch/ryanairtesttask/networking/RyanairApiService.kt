package dev.jetlaunch.ryanairtesttask.networking

import dev.jetlaunch.ryanairtesttask.networking.responsemodels.StationsResponse
import dev.jetlaunch.ryanairtesttask.networking.responsemodels.TripResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RyanairApiService {
    @GET
    suspend fun loadStations(@Url url: String = "https://tripstest.ryanair.com/static/stations.json"): StationsResponse

    @GET("availability")
    suspend fun loadSearchResult(
        @Query(value = "origin") origin: String,
        @Query("destination") destination: String,
        @Query("dateout") dateOut: String,
        @Query("datein") dateIn: String ="",
        @Query("flexdaysbeforeout") flexDaysBeforeOut: Int = 3,
        @Query("flexdaysout") flexDaysOut: Int = 3,
        @Query("flexdaysbeforein") flexDaysBeforeIn: Int = 3,
        @Query("flexdaysin") flexDaysIn: Int = 3,
        @Query("adt") adults: Int,
        @Query("teen") teens: Int,
        @Query("chd") children: Int,
        @Query("roundtrip") roundtrip: Boolean = false,
        @Query("ToUs") toUs: String = "AGREED"
    ): TripResponse
}