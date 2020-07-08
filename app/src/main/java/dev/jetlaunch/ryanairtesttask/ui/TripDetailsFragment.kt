package dev.jetlaunch.ryanairtesttask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.jetlaunch.ryanairtesttask.databinding.TripDeatailsFragmentLayoutBindingImpl
import dev.jetlaunch.ryanairtesttask.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TripDetailsFragment: Fragment() {
    companion object{
        val newInstance = TripDetailsFragment()
    }
    private val searchViewModel: SearchViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = TripDeatailsFragmentLayoutBindingImpl.inflate(inflater)
        binding.vm = searchViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}