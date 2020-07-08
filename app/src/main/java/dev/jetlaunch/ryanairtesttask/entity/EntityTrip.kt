package dev.jetlaunch.ryanairtesttask.entity

import java.text.SimpleDateFormat
import java.util.*


data class EntityTrip(
    val origin: String,
    val originName: String,
    val destination: String,
    val destinationName: String,
    val dates: List<FlightDate>
)

data class FlightDate(
    val dateOut: String,
    val flights: List<Flight>
){
    val date: Date?
    get() {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateOut)
    }
}

data class Flight(
    val faresLeft: Int,
    val flightKey: String,
    val infantsLeft: Int,
    val regularFare: RegularFare?,
    val segments: List<Segment>,
    val flightNumber: String,
    val time: List<String>,
    val timeUTC: List<Date>,
    val duration: String
)
data class Segment(
    val segmentNr: Int,
    val origin: String,
    val destination: String,
    val flightNumber: String
)

data class RegularFare(
    val fareKey: String,
    val fareClass: String,
    val fares: List<Fare>?
)
data class Fare(
    val type: String,
    val amount: Float,
    val count: Int,
    val hasDiscount: Boolean,
    val publishedFare: Float,
    val discountInPercent: Int,
    val hasPromoDiscount: Boolean
)
