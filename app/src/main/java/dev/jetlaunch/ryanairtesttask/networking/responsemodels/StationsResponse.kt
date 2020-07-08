package dev.jetlaunch.ryanairtesttask.networking.responsemodels

import com.google.gson.annotations.SerializedName
import dev.jetlaunch.ryanairtesttask.entity.EntityStation

data class StationsResponse( @SerializedName("stations") val stationsList: List<EntityStation>)