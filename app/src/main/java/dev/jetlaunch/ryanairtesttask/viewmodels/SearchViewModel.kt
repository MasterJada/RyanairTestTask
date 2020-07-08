package dev.jetlaunch.ryanairtesttask.viewmodels

import androidx.lifecycle.*
import dev.jetlaunch.ryanairtesttask.entity.EntityStation
import dev.jetlaunch.ryanairtesttask.entity.Flight
import dev.jetlaunch.ryanairtesttask.entity.FlightDate
import dev.jetlaunch.ryanairtesttask.model.StationsRepository
import dev.jetlaunch.ryanairtesttask.model.TripRepository
import dev.jetlaunch.ryanairtesttask.networking.responsemodels.TripResponse
import dev.jetlaunch.ryanairtesttask.utils.SingleLiveEvent
import dev.jetlaunch.ryanairtesttask.utils.getOr
import dev.jetlaunch.ryanairtesttask.utils.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(
    private val stationsRepository: StationsRepository,
    private val tripRepository: TripRepository
) : ViewModel() {
    val destination = MutableLiveData<EntityStation>()
    val origin = MutableLiveData<EntityStation>()
    val departureDate = MutableLiveData<Date>()

    val adults = MutableLiveData(0)
    val teens = MutableLiveData(0)
    val children = MutableLiveData(0)

    val filterMaxRate = MutableLiveData(150)

    val selectedFlight = MutableLiveData<Flight>()

    val stations = MutableLiveData<List<EntityStation>>()
    val trips = MutableLiveData<TripResponse>()

    val flights = Transformations.switchMap(filterMaxRate) { maxRate ->
        val result = MutableLiveData<List<Flight>>()
        viewModelScope.launch(Dispatchers.IO) {
            trips.value?.trips?.let { tripList ->
               result post tripList.flatMap { trip -> trip.dates.flatMap { date -> date.flights } }
                    .filter { flight ->
                        flight.regularFare?.fares?.any { it.amount <= maxRate } == true
                    }
            }

        }

        result
    }


    //events
    val errorEvent = SingleLiveEvent<String>()
    val validationError = SingleLiveEvent<String>()
    val lockUiEvent = SingleLiveEvent<Boolean>()


    private var lastNetworkCall: (() -> Unit)? = null

    fun loadStations() {
        lastNetworkCall = { loadStations() }
        lockUiEvent.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = stationsRepository.loadStations()
            lockUiEvent.postValue(false)
            if (result.error) {
                errorEvent.postValue(result.errorMessage!!)
            } else {
                stations.postValue(result.data)
            }
        }
    }

    fun searchTrips() {
        val orig = this.origin.value getOr {
            validationError.postValue("Origin shouldn't be empty")
            return
        }
        val dest = this.destination.value getOr {
            validationError.postValue("Destination shouldn't be empty")
            return
        }

        val dDate = this.departureDate.value getOr {
            validationError.postValue("Departure date shouldn't be empty")
            return
        }
        val adultsNum = this.adults.value ?: 0
        val teensNum = this.teens.value ?: 0
        val chNum = this.children.value ?: 0
        if (adultsNum + teensNum + chNum < 1) {
            validationError.postValue("You need add at least one person")
        }

        sendFetchTripsRequest(
            orig?.code!!,
            dest?.code!!,
            dateConverter(dDate!!),
            adultsNum,
            teensNum,
            chNum
        )

    }

    fun repeatLastNetworkCall() {
        lastNetworkCall?.invoke()
    }

    private fun sendFetchTripsRequest(
        originCode: String,
        destinationCode: String,
        date: String,
        adults: Int,
        teens: Int,
        children: Int
    ) {
        lastNetworkCall = { searchTrips() }
        lockUiEvent.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = tripRepository.loadTrips(
                originCode,
                destinationCode,
                date,
                adults, teens, children
            )
            lockUiEvent.postValue(false)
            if (result.error)
                errorEvent.postValue(result.errorMessage!!)
            else {
                trips.postValue(result.data)
                filterMaxRate.postValue(filterMaxRate.value)
            }

        }
    }

    private fun dateConverter(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }


}