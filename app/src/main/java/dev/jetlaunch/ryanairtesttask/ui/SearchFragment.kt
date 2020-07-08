package dev.jetlaunch.ryanairtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.jetlaunch.ryanairtesttask.R
import dev.jetlaunch.ryanairtesttask.adapters.StationsAdapter
import dev.jetlaunch.ryanairtesttask.databinding.SearchFragmentLayoutBindingImpl

import dev.jetlaunch.ryanairtesttask.entity.EntityStation
import dev.jetlaunch.ryanairtesttask.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.search_fragment_layout.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment() {

    companion object{
        val newInstance = SearchFragment()
    }

    private val searchViewModel: SearchViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = resources.getString(R.string.app_name)
        val binding = SearchFragmentLayoutBindingImpl.inflate(inflater)
        binding.vm = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.stations.observe(viewLifecycleOwner, Observer {
            tv_destination.setAdapter( StationsAdapter(context = requireContext(), objects = ArrayList(it)))
            tv_origin.setAdapter( StationsAdapter(context = requireContext(), objects = ArrayList(it)))
        })

        tv_origin.setOnItemClickListener { adapterView, _, position, _ ->
            (adapterView.adapter.getItem(position) as? EntityStation)?.let {selectedStation ->
                searchViewModel.origin.postValue(selectedStation)
            }
        }
        tv_destination.setOnItemClickListener { adapterView, _, position, _ ->
            (adapterView.adapter.getItem(position) as? EntityStation)?.let {selectedStation ->
                searchViewModel.destination.postValue(selectedStation)
            }
        }
        departure_date.minDate = Date().time
    }

}