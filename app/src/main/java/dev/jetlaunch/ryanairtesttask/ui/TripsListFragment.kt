package dev.jetlaunch.ryanairtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jetlaunch.ryanairtesttask.adapters.TripsAdapter
import dev.jetlaunch.ryanairtesttask.databinding.TripsListFragmentLayoutBindingImpl
import dev.jetlaunch.ryanairtesttask.utils.post
import dev.jetlaunch.ryanairtesttask.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.trips_list_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TripsListFragment : Fragment() {
    companion object {
        private const val CURRENCY = "CURRENCY"
        fun newInstance(currency: String): TripsListFragment {
            val params = Bundle()
            params.putString(CURRENCY, currency)
            val fragment = TripsListFragment()
            fragment.arguments = params
            return fragment
        }
    }

    private val searchViewModel: SearchViewModel by sharedViewModel()
    private lateinit var adapter: TripsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TripsListFragmentLayoutBindingImpl.inflate(inflater)
        binding.vm = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currency = arguments?.getString(CURRENCY) ?: return
        adapter = TripsAdapter(currency)
        rv_trips.layoutManager = LinearLayoutManager(requireContext())
        rv_trips.adapter = adapter
        adapter.setupClickListener {flight ->
            searchViewModel.selectedFlight post flight
        }

        searchViewModel.flights.observe(viewLifecycleOwner, Observer {
             tv_error.visibility = if(it.isEmpty()) View.VISIBLE else View.GONE
           adapter.items = it
        })
    }

}