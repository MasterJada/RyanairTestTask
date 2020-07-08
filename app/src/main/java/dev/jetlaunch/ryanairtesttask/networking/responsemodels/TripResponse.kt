package dev.jetlaunch.ryanairtesttask.networking.responsemodels

import dev.jetlaunch.ryanairtesttask.entity.EntityTrip

data class TripResponse(
    val termsOfUse: String,
    val currency: String,
    val currPrecision: Int,
    val trips: List<EntityTrip>,
    val serverTimeUTC: String
)