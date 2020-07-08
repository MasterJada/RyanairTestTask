package dev.jetlaunch.ryanairtesttask.entity

 data class EntityStation(
    val code: String,
    val name: String,
    val alternateName: String?,
    val alias: List<String>,
    val countryCode: String,
    val countryName: String,
    val countryAlias: String?,
    val countryGroupCode: String,
    val countryGroupName: String,
    val timeZoneCode: String,
    val latitude: String,
    val longitude: String,
    val mobileBoardingPass: Boolean,
    val markets: List<Market>,
    val notices: String?,
    val tripCardImageUrl: String
){
     override fun toString(): String {
         return code
     }
 }

data class Market(val code: String, val group: String?,val stops: List<MarketStop>)
data class MarketStop(val code: String)