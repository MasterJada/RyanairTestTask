package dev.jetlaunch.ryanairtesttask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.jetlaunch.ryanairtesttask.R
import dev.jetlaunch.ryanairtesttask.entity.Flight
import dev.jetlaunch.ryanairtesttask.entity.FlightDate
import dev.jetlaunch.ryanairtesttask.utils.toString


class TripsAdapter(private val currency: String) : RecyclerView.Adapter<TripsAdapter.TripVH>() {

    var items: List<Flight> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var clickCallback: ((Flight) -> Unit)? = null


    fun setupClickListener(listener: ((Flight) -> Unit)) {
        clickCallback = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripVH {
        return TripVH(
            LayoutInflater.from(parent.context).inflate(R.layout.trip_item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TripVH, position: Int) {
        val flight = items[position]

        flight.time.firstOrNull()?.let { date ->
            holder.date.text = date.substringBefore("T")
        }
        holder.itemView.setOnClickListener {
            clickCallback?.invoke(flight)
        }

        holder.duration.text = flight.duration
        holder.fare.text = flight.regularFare?.fares?.first()?.amount.toString() + currency
        holder.flightNumber.text = flight.flightNumber

    }

    class TripVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fare: TextView = itemView.findViewById(R.id.tv_fare)
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val flightNumber: TextView = itemView.findViewById(R.id.tv_flight_number)
        val duration: TextView = itemView.findViewById(R.id.tv_duration)
    }
}