package dev.jetlaunch.ryanairtesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dev.jetlaunch.ryanairtesttask.ui.LockUIFragment
import dev.jetlaunch.ryanairtesttask.ui.SearchFragment
import dev.jetlaunch.ryanairtesttask.ui.TripDetailsFragment
import dev.jetlaunch.ryanairtesttask.ui.TripsListFragment
import dev.jetlaunch.ryanairtesttask.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance)
                .commit()

        searchViewModel.loadStations()

        searchViewModel.validationError.observe(this, Observer {
            Snackbar.make(container, it, Snackbar.LENGTH_SHORT).show()
        })

        searchViewModel.errorEvent.observe(this, Observer {
            Snackbar.make(container, it, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry") {
                    searchViewModel.repeatLastNetworkCall()
                }.show()
        })

        searchViewModel.lockUiEvent.observe(this, Observer { lock ->
            if (lock)
                supportFragmentManager.beginTransaction()
                    .add(R.id.container, LockUIFragment.newInstance)
                    .commit()
            else supportFragmentManager.beginTransaction()
                .remove(LockUIFragment.newInstance)
                .commit()
        })

        searchViewModel.trips.observe(this, Observer {
            val origin = it.trips.first().originName
            val destination = it.trips.first().destinationName

            title = "$origin -> $destination"
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TripsListFragment.newInstance(it.currency))
                .addToBackStack("TripsFragment")
                .commit()
        })

        searchViewModel.selectedFlight.observe(this, Observer {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TripDetailsFragment.newInstance)
                .addToBackStack("TripDetatilsFragment")
                .commit()
        })
    }



}